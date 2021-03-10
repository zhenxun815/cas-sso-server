package com.xgh.cas.login.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description
 *
 * @Copyright: Copyright (c) 2020-2027 郑州象过河软件技术有限公司 All rights reserved.
 * @author: liuguobao(aika_liu @ 163.com)
 * @date: 2021/3/5 14:48
 * 扫码注册
 */
@Controller
@Slf4j
public class ScanReg2Controller {
    @RequestMapping("/registerScanNext")
    public String register(Model model) {
        return "registerScanNext";
    }

}
