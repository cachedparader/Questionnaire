package com.qqcn.sms.service.impl;

import com.qqcn.common.utils.AliyunSmsUtil;
import com.qqcn.sms.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private AliyunSmsUtil aliyunSmsUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void getCaptcha(String phone) {
        // 6位随机数
        Random random = new Random();
        String code = String.format("%06d", random.nextInt(1000000));
        // 调用短信工具类发短信
        //aliyunSmsUtil.sendSms(phone,code);
        log.debug("-----> 验证码：" + code);
        // 存入redis
        redisTemplate.opsForValue().set(capthcaKey(phone,code),code,5, TimeUnit.MINUTES);
    }




    private String capthcaKey(String phone, String code){
        return "sms:" + phone + ":" + code;
    }




    @Override
    public boolean checkCaptcha(String phone, String code) {
        Object obj =  redisTemplate.opsForValue().get(capthcaKey(phone, code));
        return obj != null ? true : false;
    }
}
