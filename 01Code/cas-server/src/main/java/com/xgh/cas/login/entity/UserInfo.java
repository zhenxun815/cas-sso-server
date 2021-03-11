package com.xgh.cas.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Description
 *
 * @Copyright: Copyright (c) 2020-2027 郑州象过河软件技术有限公司 All rights reserved.
 * @author: liuguobao(aika_liu @ 163.com)
 * @date: 2021/3/9 11:13
 */
@Data
@TableName("cas_user_info")
public class UserInfo {
    /**
     * ID
     */
    private static final long serialVersionUID = -2857309910343526897L;
    @TableId(value = "id",type = IdType.AUTO)
    private String id;

    /**
     * 手机号
     */

    private String phoneNumber;

    /**
     * 密码
     */
    private String password;


}
