package com.qqcn.security.service;

import com.qqcn.security.entity.SecurityUser;
import com.qqcn.user.entity.User;
import com.qqcn.user.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("-----> loadUserByUsername: " + username);
        User user = userMapper.getUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }


        return new SecurityUser(user);
    }
}
