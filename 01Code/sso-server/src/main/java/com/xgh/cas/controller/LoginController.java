package com.xgh.cas.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xgh.cas.config.CasConfig;
import com.xgh.cas.server.TgtServer;
import com.xgh.cas.utils.CasServerUtil;
import com.xgh.cas.viewmodel.res.UserCheckResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Description
 *
 * @Copyright: Copyright (c) 2020-2027 郑州象过河软件技术有限公司 All rights reserved.
 * @author: liuguobao
 * @date: 2021/1/18 10:26
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private TgtServer tgtServer;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * CAS 登录认证
     */
    @PostMapping("/loginRest")
    public Object login(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String service = request.getParameter("service");

        // 获取 TGT
        String tgt = CasServerUtil.getTGT(username, password);
        if (tgt == null){
            return new ResponseEntity("用户名或密码错误。", HttpStatus.BAD_REQUEST);
        }

        // 设置cookie
        Cookie cookie = new Cookie(CasConfig.COOKIE_NAME, username + "@" + tgt);
        // Cookie有效时间（1小时）
        cookie.setMaxAge(CasConfig.COOKIE_VALID_TIME);
        // Cookie有效路径
        cookie.setPath("/");
        // 只允许服务器获取cookie
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        // 将当前用户的TGT信息存储在Redis上
        tgtServer.setTGT(username, tgt, CasConfig.COOKIE_VALID_TIME);
        // 当Service 为空时，直接返回 TGT
        if(StringUtils.isEmpty(service)){
            return new ResponseEntity(tgt,HttpStatus.CREATED);
        }

        // 获取 ST
        String st = CasServerUtil.getST(tgt, service);
        if (st==null){
            return new ResponseEntity("用户名或密码错误。", HttpStatus.BAD_REQUEST);
        }

        // 302重定向最后授权
        String redirectUrl = service + "?ticket=" + st;
        return "redirect:" + redirectUrl;
    }

    /**
     * CAS 登录验证
     */
    @PostMapping("/validateLogin")
    public Object validateLogin(String username , String tgt) throws Exception {

        if(StringUtils.isEmpty(username)){
            return new ResponseEntity("登录验证失败，用户名不能为空！",HttpStatus.BAD_REQUEST);
        }
        if(StringUtils.isEmpty(tgt)){
            return new ResponseEntity("登录验证失败，票据信息不能为空！",HttpStatus.BAD_REQUEST);
        }

        // 获取Redis值
        String value = tgtServer.getTGT(username);
        // 匹配Redis中的TGT与接收的TGT是否相等
        if (tgt.equals(value)) {
            tgtServer.setTGT(username,tgt,CasConfig.COOKIE_VALID_TIME);
            return new ResponseEntity("validateLogin is ok",HttpStatus.OK);
        }
        return new ResponseEntity("登录验证失败，请重新登录！",HttpStatus.BAD_REQUEST);
    }

    /**
     * CAS 更新TGT
     */
    @PostMapping("/verifyTgt")
    public Object verifyTgt(String username , String tgt) throws Exception {

        if(StringUtils.isEmpty(username)){
            return new ResponseEntity("更新失败，用户名不能为空！",HttpStatus.OK);
        }
        if(StringUtils.isEmpty(tgt)){
            return new ResponseEntity("更新失败，票据信息（TGT）不能为空！",HttpStatus.OK);
        }

        // 获取Redis值
        String value = tgtServer.getTGT(username);
        // 匹配Redis中的TGT与接收的TGT是否相等
        if (tgt.equals(value)) {
            tgtServer.setTGT(username,tgt,CasConfig.COOKIE_VALID_TIME);
        }else{
            return new ResponseEntity("票据信息失效，最新票据信息为："+value,HttpStatus.OK);
        }

        return new ResponseEntity(tgt,HttpStatus.OK);
    }

    /**
     * 检查用户是否登录过
     */
    @RequestMapping("/check")
    @ResponseBody
    public String checkLoginUser(HttpServletRequest request) throws Exception {

        String service = request.getParameter("service");
        String callback = request.getParameter("callback");
        Cookie[] cookies = request.getCookies();
        String username = null;
        String tgt = null;

        UserCheckResponse result = new UserCheckResponse();

        if (cookies != null) {
            System.out.println(new Gson().toJson(cookies));

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(CasConfig.COOKIE_NAME)) {
                    username = cookie.getValue().split("@")[0];
                    tgt = cookie.getValue().split("@")[1];
                    break;
                }
            }

            if (username != null) {
                // 获取Redis值
                String value = tgtServer.getTGT(username);
                // 匹配Redis中的TGT与Cookie中的TGT是否相等
                if (tgt.equals(value)) {

                    // 获取 ST
                    String st = CasServerUtil.getST(tgt, service);
                    result.setStatus(1);
                    result.setData(service + "?ticket=" + st);
                }
            }
        }

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String tmp = "";
        if(!StringUtils.isEmpty(callback)){
            tmp = callback + "(" + gson.toJson(result) + ")";
        }else{
            tmp = gson.toJson(result);
        }

        return tmp;
    }

    /**
     * 因为TGT在SSO服务端维护，并不在CAS-Server，所以只需要把redis中匹配的tgt信息删除即可。
     */
    @GetMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request) {
        String callback = request.getParameter("callback");
        Cookie[] cookies = request.getCookies();
        String username = null;
        String tgt = null;

        if (cookies != null) {

            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(CasConfig.COOKIE_NAME)) {
                    username = cookie.getValue().split("@")[0];
                    tgt = cookie.getValue().split("@")[1];
                    break;
                }
            }

            if (username != null) {
                // 获取Redis值
                String value = tgtServer.getTGT(username);

                // 匹配Redis中的TGT与Cookie中的TGT是否相等
                if (tgt.equals(value)) {
                    // 删除TGT
                    tgtServer.delTGT(username);
                    tgtServer.delTGT("CAS_TICKET:"+value);
                }
            }
        }

        String tmp = callback + "({'code':'0','msg':'登出成功'})";

        return null;
    }

}
