package com.xgh.cas.config;

public class CasConfig {

    /**
     * CAS登录地址的token
     */
    //public static String GET_TOKEN_URL = "https://xgohe.f3322.net:8443/cas/v1/tickets";
    public static String GET_TOKEN_URL = "https://192.168.2.98:8443/cas/v1/tickets";
    //public static String GET_TOKEN_URL = "https://cas.server.com:8443/cas/v1/tickets";

    /**
     * 设置Cookie的有效时长（1小时）
     */
    public static int COOKIE_VALID_TIME = 1 * 60 * 60;

    /**
     * 设置Cookie的有效时长（1小时）
     */
    public static String COOKIE_NAME = "UToken";

}
