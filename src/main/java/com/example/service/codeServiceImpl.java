package com.example.service;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import com.example.dto.codeDto;
import com.example.util.redisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class codeServiceImpl {
    @Autowired
    codeDto codeDto;
    @Autowired
    redisUtil redisUtil;
    public codeDto getCode(){
        String uuid = UUID.randomUUID().toString();
        //验证码
        RandomGenerator randomGenerator = new RandomGenerator("0123456789",4);

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100,40);
        lineCaptcha.setGenerator(randomGenerator);
        String code = lineCaptcha.getCode();
        //将uuid与code真实值以键值对形式放置于redis中
        redisUtil.set(uuid,code);
        String base64 = lineCaptcha.getImageBase64();
        //封装到DTO并返回
        codeDto.setUuid(uuid);
        codeDto.setBase64(base64);
        return  codeDto;
    }
}
