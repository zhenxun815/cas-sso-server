package com.xgh.cas.login.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xgh.cas.login.entity.SysUser;

import java.util.List;

/**
 * Description
 *
 * @Copyright: Copyright (c) 2020-2027 郑州象过河软件技术有限公司 All rights reserved.
 * @author: liuguobao(aika_liu @ 163.com)
 * @date: 2021/3/9 11:11
 */
public interface ISysUserService extends IService<SysUser> {
    public List<SysUser> getSysUserInfo();
}
