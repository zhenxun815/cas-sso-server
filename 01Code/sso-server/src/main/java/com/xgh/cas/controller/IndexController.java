package com.xgh.cas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index(){
        return "SSO-Server 认证中心，用于和 CAS-Server 进行交互，对外提供接口";
    }

}
