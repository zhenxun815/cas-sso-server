package com.xgh.cas.login.controller;

import com.xgh.cas.message.entity.MessageEntity;
import com.xgh.cas.message.util.SmsCodeUtil;
import com.xgh.cas.utils.CheckUtil;
import com.xgh.cas.utils.FuncUtil;
import com.xgh.cas.utils.MD5Util;
import com.xgh.cas.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Description
 *
 * @Copyright: Copyright (c) 2020-2027 郑州象过河软件技术有限公司 All rights reserved.
 * @author: liuguobao(aika_liu @ 163.com)
 * @date: 2021/3/5 14:48
 * 忘记密码
 */
@Controller
@Slf4j
public class ResetPwdController {
    @RequestMapping("/forgetPassword")
    public String register(Model model) {
        return "forgetPassword";
    }

}
