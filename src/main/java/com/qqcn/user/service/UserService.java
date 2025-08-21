package com.qqcn.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qqcn.common.vo.Result;
import com.qqcn.user.entity.User;

import java.util.Map;

public interface UserService extends IService<User> {
    public void registerUser(User user);

    User getUserInfo(String token);

    void updateNickname(Integer userId, String nickname);

    Map<String, Object> getNewToken(String token);

    Result updatePassword(int userId, String password, String newPassword);

    void updateAvatar(int userId, String filename);
}
