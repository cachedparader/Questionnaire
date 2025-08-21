package com.qqcn.common.constant;

import lombok.Data;


public enum ResultConstant {
    SUCCESS(200,"操作成功"),
    FAIL(201,"操作失败"),

    FAIL_LOGIN(202,"用户名或密码错误"),

    FAIL_UNLOGIN(203,"登录状态无效，请登录"),

    FAIL_DENIED(204,"权限不足"),

    FAIL_PASSWORD(205,"密码错误"),
    FAIL_FILE_UPLOAD_NULL(206,"上传文件为空"),
    FAIL_FILE_UPLOAD_ERROR(207,"上传文件失败"),

    FAIL_SYSTEM_EXCEPTION(299,"系统异常，请联系管理员");


    private Integer code;
    private String message;


    private ResultConstant(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }
    public String getMessage(){
        return this.message;
    }
}
