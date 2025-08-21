package com.qqcn.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;

@Data
@TableName("wj_user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String nickname;
    private String phone;
    private String password;
    private String email;
    private String wechat;
    private java.util.Date fcd;
    private java.util.Date lud;
    private String userType;
    private String avatar;
    private String avatarData;

    @TableField(exist = false)
    private String userTypeDesc;

    public String getUserTypeDesc(){
        if("normal".equals(userType)){
            return "普通用户";
        }else if("admin".equals(userType)){
            return "管理员";
        }else{
            return "其它";
        }
    }

    @TableField(exist = false)
    private String avatarUrl;
}