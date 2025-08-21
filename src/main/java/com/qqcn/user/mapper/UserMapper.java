package com.qqcn.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qqcn.user.entity.User;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from wj_user where username=#{username} || phone = #{username}")
    User getUserByName(String username);

    @Update("update wj_user set nickname=#{nickname} where id = #{userId}")
    void updateNickname(Integer userId, String nickname);

    @Update("update wj_user set password=#{password} where id = #{userId}")
    void updatePassword(int userId, String password);

    @Update("update wj_user set avatar=#{avatar} where id = #{userId}")
    void updateAvatar(int userId, String avatar);

}
