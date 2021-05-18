package com.xgh.cas.login.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xgh.cas.login.entity.SysUser;
import com.xgh.cas.login.entity.UserInfo;
import com.xgh.cas.login.mapper.SysUserMapper;
import com.xgh.cas.login.mapper.UserInfoMapper;
import com.xgh.cas.login.service.IRegisterService;
import com.xgh.cas.login.service.ISysUserService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Description
 *
 * @Copyright: Copyright (c) 2020-2027 郑州象过河软件技术有限公司 All rights reserved.
 * @author: liuguobao(aika_liu @ 163.com)
 * @date: 2021/3/9 11:11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public List<SysUser> getSysUserInfo() {
        return null;
    }
}
