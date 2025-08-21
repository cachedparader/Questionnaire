package com.qqcn.sms.service;

public interface SmsService {

    void getCaptcha(String phone);

    boolean checkCaptcha(String phone, String code);
}
