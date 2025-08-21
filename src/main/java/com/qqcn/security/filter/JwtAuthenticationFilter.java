package com.qqcn.security.filter;

import com.qqcn.common.utils.JwtUtils;
import com.qqcn.security.entity.SecurityUser;
import com.qqcn.user.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 放行部分请求
        String uri = request.getRequestURI();
        log.debug("--------> jwt 验证开始：" + uri);
        if( !uri.endsWith("/user/login")
            && uri.indexOf("/user/info") < 0
                && !uri.endsWith("/user/logout")
                && !uri.endsWith("/user/reg")
                && uri.indexOf("/sms") < 0
                && uri.indexOf("/survey/exam") < 0){
            // 2. 获取并验证jwt
            String jwt = request.getHeader("Authorization");
            if(StringUtils.hasLength(jwt)){
                User user = jwtUtils.parsseJwt(jwt, User.class);
                SecurityUser securityUser = new SecurityUser(user);
                if(user != null){
                    // 3. jwt有效，告诉security这是一个合法请求
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(securityUser,null,securityUser.getAuthorities())
                    );
                    log.debug("--------> jwt 验证通过：" + uri);
                }
            }

        }
        filterChain.doFilter(request,response);
    }
}
