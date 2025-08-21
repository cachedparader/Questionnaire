package com.qqcn.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qqcn.common.constant.ResultConstant;
import com.qqcn.common.utils.JwtUtils;
import com.qqcn.common.vo.Result;
import com.qqcn.file.utils.MinioUtils;
import com.qqcn.user.entity.User;
import com.qqcn.user.mapper.UserMapper;
import com.qqcn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    //@Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void registerUser(User user) {
        user.setUsername(user.getPhone());
        user.setNickname(user.getPhone());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        baseMapper.insert(user);
    }

    @Override
    public User getUserInfo(String token) {
        User user = jwtUtils.parsseJwt(token, User.class);
        return user;
    }

    @Override
    public void updateNickname(Integer userId, String nickname) {
        baseMapper.updateNickname(userId, nickname);
    }

    @Autowired
    private MinioUtils minioUtils;

    @Override
    public Map<String, Object> getNewToken(String token) {
        User user = jwtUtils.parsseJwt(token, User.class);
        User newUser = baseMapper.selectById(user.getId());
        newUser.setAvatarUrl(minioUtils.getUrl(newUser.getAvatar()));
        String newToken = jwtUtils.createJwt(newUser);
        return Map.of("user",newUser,"token",newToken);
    }

    @Override
    public Result updatePassword(int userId, String password, String newPassword) {
        // 查询用户
        User user = baseMapper.selectById(userId);
        // 比较原密码
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (!matches){
            return Result.fail(ResultConstant.FAIL_PASSWORD.getCode(),ResultConstant.FAIL_PASSWORD.getMessage());
        }
        // 更新密码
        baseMapper.updatePassword( userId, passwordEncoder.encode(newPassword));
        return Result.success("密码修改成功");
    }

    @Override
    public void updateAvatar(int userId, String filename) {
        baseMapper.updateAvatar(userId,filename);
    }

}
