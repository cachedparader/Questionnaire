package com.qqcn.security.config;

import com.alibaba.fastjson2.JSON;
import com.qqcn.common.constant.ResultConstant;
import com.qqcn.common.utils.JwtUtils;
import com.qqcn.common.vo.Result;
import com.qqcn.file.utils.MinioUtils;
import com.qqcn.security.entity.SecurityUser;
import com.qqcn.security.filter.JsonUsernamePasswordAuthenticationFilter;
import com.qqcn.security.filter.JwtAuthenticationFilter;
import com.qqcn.user.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 放行部分请求
        http.authorizeHttpRequests(request -> {
            request.requestMatchers("/user/login",
                    "/user/info",
                    "/user/logout",
                    "/user/reg",
                    "/swagger-ui/**",
                    "/swagger-resources/**",
                    "/v3/**",
                    "/survey/exam/**",
                    "/sms/**").anonymous() // 允许匿名访问
                    .anyRequest().authenticated(); //其余请求均需认证
        });

        // 请求url
        /*http.formLogin(form -> {
            form.loginProcessingUrl("/user/login")
                    .successHandler((request, response, authentication) -> {
                        response.getWriter().write("login success");
                    });
        });*/

        // 前后端分离，无状态
        http.sessionManagement(session -> {
            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        });

        // 关闭csrf
        http.csrf(csrf -> {
            csrf.disable();
        });

        // cors
        http.cors(cors -> {});

        // 注册JsonUsernamePasswordAuthenticationFilter
        http.addFilterAt(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        http.logout(logout -> {
            logout.logoutUrl("/user/logout")
                    .logoutSuccessHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=utf-8");
                        Result result = Result.success();
                        response.getWriter().write(JSON.toJSONString(result));
                    });
        });


        http.exceptionHandling(exception -> {
            // 未登录处理
            exception.authenticationEntryPoint((request, response, authException) -> {
                response.setContentType("application/json;charset=utf-8");
                Result<Object> result = Result.fail(ResultConstant.FAIL_UNLOGIN.getCode(), ResultConstant.FAIL_UNLOGIN.getMessage());
                response.getWriter().write(JSON.toJSONString(result));
            });
            // 权限不足
            exception.accessDeniedHandler((request, response, accessDeniedException) -> {
                response.setContentType("application/json;charset=utf-8");
                Result<Object> result = Result.fail(ResultConstant.FAIL_DENIED.getCode(), ResultConstant.FAIL_DENIED.getMessage());
                response.getWriter().write(JSON.toJSONString(result));
            });
        });

        // 注册jwt验证过滤器
        http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MinioUtils minioUtils;

    private JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() throws Exception {
        JsonUsernamePasswordAuthenticationFilter filter = new JsonUsernamePasswordAuthenticationFilter();

        filter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        filter.setFilterProcessesUrl("/user/login");
        // 认证成功处理
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            // 获取登录的用户对象
            SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
            // 创建jwt
            User user = securityUser.getUser();
            user.setAvatarUrl(minioUtils.getUrl(user.getAvatar()));
            String token = jwtUtils.createJwt(user);
            // 返回json
            Result<String> result = Result.success(token, "登录成功");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(result));
        });
        // 认证失败处理
        filter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            Result<Object> result = Result.fail(ResultConstant.FAIL_LOGIN.getCode(), ResultConstant.FAIL_LOGIN.getMessage());
            response.getWriter().write(JSON.toJSONString(result));
        });

        return filter;
    }

}
