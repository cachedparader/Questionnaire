package com.qqcn.user.controller;

import com.qqcn.common.constant.ResultConstant;
import com.qqcn.common.vo.Result;
import com.qqcn.user.entity.User;
import com.qqcn.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "用户模块",description = "用户模块接口")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "查询所有用户")
    @GetMapping
    @Secured({"ROLE_admin"})
    public Result<List<User>> getAllUser(){
        //int a = 5/0;
        List<User> list = userService.list();
        return Result.success(list);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/reg")
    public Result<?> registerUser(@RequestBody User user){
        userService.registerUser(user);
        return Result.success("用户注册成功");
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/info")
    public Result<User> getUserInfo(@RequestParam("token") String token){
        User user =  userService.getUserInfo(token);
        if(user == null){
            return Result.fail(ResultConstant.FAIL_UNLOGIN.getCode(),ResultConstant.FAIL_UNLOGIN.getMessage());
        }
        return Result.success(user);
    }

    @Operation(summary = "昵称修改")
    @PutMapping("/nickname")
    public Result<?> updateNickname(@RequestParam("userId") Integer userId,
                                    @RequestParam("nickname") String nickname){
        userService.updateNickname(userId,nickname);
        return Result.success("修改昵称成功");
    }

    @Operation(summary = "获取新token")
    @GetMapping("/token")
    public Result<?> getNewToken(@RequestHeader("Authorization") String token){
        Map<String,Object> result =  userService.getNewToken(token);
        return Result.success(result);
    }

    @Operation(summary = "修改密码")
    @PutMapping("/password")
    public Result<?> updatePassword(@RequestBody Map param){
        Result result = userService.updatePassword(Integer.parseInt(param.get("userId")+""),
                param.get("password")+"",
                param.get("newPassword")+"");
        return  result;
    }

    @Operation(summary = "修改头像")
    @PutMapping("/avatar")
    public Result<?> updateAvatar(@RequestBody Map param){
        userService.updateAvatar(Integer.parseInt(param.get("userId")+""),
                param.get("filename")+"");
        return Result.success();
    }

}
