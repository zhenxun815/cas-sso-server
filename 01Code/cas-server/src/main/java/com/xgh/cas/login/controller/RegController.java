package com.xgh.cas.login.controller;

import com.xgh.cas.login.entity.UserInfo;
import com.xgh.cas.message.entity.MessageEntity;
import com.xgh.cas.message.util.SmsCodeUtil;
import com.xgh.cas.utils.CheckUtil;
import com.xgh.cas.utils.FuncUtil;
import com.xgh.cas.utils.MD5Util;
import com.xgh.cas.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description
 *
 * @Copyright: Copyright (c) 2020-2027 郑州象过河软件技术有限公司 All rights reserved.
 * @author: liuguobao(aika_liu @ 163.com)
 * @date: 2021/3/5 14:48
 */
@RestController
@Slf4j
public class RegController {

    @GetMapping("/register")
    public Result<?> register(@RequestParam String phoneNumber, String code, String passWord){
        if(StringUtils.isEmpty(phoneNumber) ){
            return Result.error("手机号不能为空！");
        }
        if(StringUtils.isEmpty(code)){
            return Result.error("验证码不能为空！");
        }
        if(StringUtils.isEmpty(passWord)){
            return Result.error("密码不能为空！");
        }
        //验证手机号是否合法
        if(!CheckUtil.isMobile(phoneNumber)){
            return Result.error("请输入正确的手机号！");
        }
        //校验验证码
        if(!SmsCodeUtil.isSmsCode("",code)){
            return Result.error("验证码错误！");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhoneNumber(phoneNumber);
        /*int i = registerService.selectCount(userInfoEntityWrapper);
        if(registerService.selectCount(userInfoEntityWrapper) > 0){
            return Result.error("当前手机号已经注册！");
        }*/

        String smsCode = "";
        //校验验证码是否正确

        System.out.println("-----接收到的参数："+phoneNumber+","+code+","+passWord);
        //MD5 加密
        //MD5Util.getMD5("123456");
        System.out.println("MD5 加密：==============>"+MD5Util.getMD5(passWord));


        return Result.OK("只是一个简单的测试！");
    }

    /**
     * 发送验证码
     */
    @PostMapping("/sendSmsCode")
    public Result<?> sendSmsCode(@RequestParam String phoneNumber,String sign){
        if(StringUtils.isEmpty(phoneNumber)){
            return Result.error("手机号不能为空");
        }
        if(!CheckUtil.isMobile(phoneNumber)){
            return Result.error("请输入正确的手机号");
        }
        //发送验证码
        String appid = "7648eb564c5";
        String secret = "eccbc87e4b5ce2fe28308";
        String sign_ = MD5Util.getMD5("appid" + appid + "mobile" + phoneNumber + secret);
        System.out.println("=================>"+sign_);
        //校验密钥
        if (!sign_.equals(sign)) {
            return Result.error("密钥校验失败！");
        }
        //发送短信
        String smsCode = "" + FuncUtil.getRandInt(1111, 9999);
        MessageEntity message = SmsCodeUtil.sendMessage(phoneNumber, smsCode);

        //发送失败
        if (message == null) {
            return Result.error("发送短信验证码失败!");

        }


        return Result.OK(message);
    }
}
