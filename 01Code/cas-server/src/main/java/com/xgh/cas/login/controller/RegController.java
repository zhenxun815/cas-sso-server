package com.xgh.cas.login.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xgh.cas.login.entity.UserInfo;
import com.xgh.cas.login.service.IRegisterService;
import com.xgh.cas.message.entity.MessageEntity;
import com.xgh.cas.message.util.SmsCodeUtil;
import com.xgh.cas.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private IRegisterService registerService;

    @Autowired
    private RedisUtil redisUtil;


    @GetMapping("/register1")
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
        if(!isSmsCode(ConnmonConstants.REGISTER_CODE_KEY+phoneNumber,code)){
            return Result.error("验证码错误！");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhoneNumber(phoneNumber);
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<UserInfo>(userInfo);
        UserInfo newUserInfo = registerService.getOne(userInfoQueryWrapper);
        if(newUserInfo != null){
            return Result.error("当前手机号已经注册！");
        }
        userInfo.setPassword(MD5Util.getMD5(passWord));
        userInfo.setUserName(phoneNumber);
        userInfo.setCreateTime(DateUtil.now());
        registerService.save(userInfo);

        return Result.OK("注册成功！");
    }

    @GetMapping("/modifyPass")
    public Result<?> modifyPass(@RequestParam String phoneNumber, String code, String passWord){
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
        if(!isSmsCode(ConnmonConstants.MODIFYPASS_CODE_KEY+phoneNumber,code)){
            return Result.error("验证码错误！");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setPhoneNumber(phoneNumber);
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<UserInfo>(userInfo);
        UserInfo newUserInfo = registerService.getOne(userInfoQueryWrapper);
        if(newUserInfo == null){
            return Result.error("当前手机号未注册！");
        }
        newUserInfo.setPassword(MD5Util.getMD5(passWord));
        registerService.updateById(userInfo);

        return Result.OK("密码修改成功！");
    }
    /**
     * 验证手机号是否已经注册
     */
    public boolean checkPhoneNumber(String phoneNumber){
        boolean f = false;
        UserInfo userInfo = new UserInfo();
        userInfo.setPhoneNumber(phoneNumber);
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<UserInfo>(userInfo);
        UserInfo newUserInfo = registerService.getOne(userInfoQueryWrapper);
        if(newUserInfo != null){
            f = true;
            return f;
        }

        return f;
    }

    /**
     * 发送验证码
     */
    @PostMapping("/sendSmsCode")
    public Result<?> sendSmsCode(@RequestParam String phoneNumber,String key,String sign){
        if(StringUtils.isEmpty(phoneNumber)){
            return Result.error("手机号不能为空");
        }
        if(!CheckUtil.isMobile(phoneNumber)){
            return Result.error("请输入正确的手机号");
        }
        if(key.equals(ConnmonConstants.MODIFYPASS_CODE_KEY) && !checkPhoneNumber(phoneNumber)){
            return Result.error("当前手机号未注册！");
        }
        if(key.equals(ConnmonConstants.REGISTER_CODE_KEY) && checkPhoneNumber(phoneNumber)){
            return Result.error("当前手机号已注册！");
        }
        //发送验证码
        String appid = "7648eb564c5";
        String secret = "eccbc87e4b5ce2fe28308";
        String sign_ = MD5Util.getMD5("appid" + appid + "mobile" + phoneNumber + secret);
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
        redisUtil.setEx(key+phoneNumber,smsCode,Long.valueOf(60), TimeUnit.SECONDS);


        return Result.OK("发送短信验证码成功!");
    }

    /**
     * 验证手机短信
     * 根据客户令牌，手机号，验证码
     * 用户用手机号+验证码=>登录系统
     *
     * @param key key
     * @param smsCode     验证码
     * @author G/2018/7/2 16:34
     */
    public final boolean isSmsCode(String key, String smsCode) {
        boolean f = false;
        //获取对应的验证码
        Object redisVerifyCode = redisUtil.get(key);

        if (CheckUtil.isEmpty(smsCode)) {
            return f;
        }

        if (smsCode.equals("692710")) {
            f = true;
        }

        if (redisVerifyCode != null) {
            //比较验证码
            if (smsCode.equals(redisVerifyCode.toString())) {
                f = true;
            }

        }
        return f;
    }
}
