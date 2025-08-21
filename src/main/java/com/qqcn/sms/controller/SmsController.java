package com.qqcn.sms.controller;

import com.qqcn.common.vo.Result;
import com.qqcn.sms.service.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "短信模块",description = "短信模块接口")
@RestController
public class SmsController {

    @Autowired
    private SmsService smsService;


    @Operation(summary = "获取验证码")
    @GetMapping("/sms/{phone}")
    public Result<?> getCaptcha(@PathVariable("phone") String phone){
        smsService.getCaptcha(phone);
        return Result.success();
    }

    @Operation(summary = "校验验证码")
    @GetMapping("/sms/check")
    public Result<?> checkCapthcha(@RequestParam("phone") String phone,
                                   @RequestParam("code") String code){
        boolean isValid =  smsService.checkCaptcha(phone,code);
        return Result.success(isValid);
    }
}
