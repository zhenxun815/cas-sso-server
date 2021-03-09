package com.xgh.cas.message.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: shop_message 消息
 * @Copyright Copyright (c) 2020-2028 郑州象过河软件技术有限公司 All rights reserved.
 * @author
 * @date 2020-06-29 03:05:33
 */
@Getter
@Setter
public class MessageEntity {


	private static final long serialVersionUID = -2857309910343526897L;
	/**
	 * app唯一标识
	 */
	private String appid;
	/**
	 * 消息内容
	 */
	private String content;
	/**
	 * 手机号码
	 */
	private String mobilePhone;
	/**
	 * 发送的短信验证码
	 */
	private String smsCode;
	/**
	 * （0：失败  1：成功  2：未知）
	 */
	private Integer status;

}
