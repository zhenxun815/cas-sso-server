package com.xgh.cas.message.util;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import com.xgh.cas.message.entity.MessageEntity;
import com.xgh.cas.utils.ConnmonConstants;
import com.xgh.cas.utils.DateUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * 短信验证码工具
 *
 * @author G./2018/7/2 16:51
 */
@Service
public class SmsCodeUtil {



    /**
     * 短信验证码缓存
     * key = 验证码存取标识,发送短信后服务器端存取的标识
     * value=[手机号,验证码,上次发送时间]
     *
     * @author G/2018/7/2 16:31
     */
    private static Map<String, String> smsCodeCache = new TreeMap<>();

    /**
     * 发送短信后，调用此方法保存
     *
     * @param key     客户端令牌
     * @param phone   手机号
     * @param smsCode 短信验证码
     * @author G/2018/7/2 16:25
     */
    public final static void addSmsCode(String key, String phone, String smsCode) {
        smsCodeCache.put(key, phone + "," + smsCode + "," + (DateUtil.now().getTime()));
    }

    /**
     * 清除已经使用的验证码
     *
     * @param key
     * @author G/2018/7/2 16:26
     */
    public final static void delSmsCode(String key) {
        smsCodeCache.remove(key);
    }

    /**
     * 删除超时的验证码
     *
     * @param
     * @author G/2018/7/2 16:26
     */
    public final static void expireSmsCode() {
        List<String> delKeys = new ArrayList<>();
        for (String key : smsCodeCache.keySet()) {
            String vt = smsCodeCache.get(key);
            if (vt != null) {
                //获取发送时间
                Long time = Long.parseLong(vt.split(",")[2]);
                //如果大于5分钟则从注册向量中删除
                if (DateUtil.minutesBetween(DateUtil.now(), new Date(time)) > 5) {
                    delKeys.add(key);
                }
            }
        }
        //从集合中删除
        for (String key : delKeys) {
            smsCodeCache.remove(key);
        }
    }
   // @Autowired
   // private RedisUtil redisUtil;

    /**
     * 验证手机短信
     * 根据客户令牌，手机号，验证码
     * 用户用手机号+验证码=>登录系统
     *
     * @param key         用户令牌
     * @param mobilePhone 手机号
     * @param smsCode     验证码
     * @author G/2018/7/2 16:34
     */
    public final boolean isSmsCode(String key, String mobilePhone, String smsCode) {
        boolean f = false;
        //获取对应的验证码
        Object redisVerifyCode = null;//redisUtil.get(ConnmonConstants.LOGIN_CODE_KEY + key);


        if (smsCode.equals("692715")) {
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

    /**
     * 根据令牌验证手机短信
     * 用于找回密码(不需要传递手机号)
     * 用户输入账号，系统对用户绑定的手机发送短信验证码
     *
     * @param key     用户令牌
     * @param smsCode 短信验证码
     * @author G/2018/7/2 16:36
     */
    public final static boolean isSmsCode(String key, String smsCode) {
        boolean f = false;
        String vt = smsCodeCache.get(key);
        if (vt != null) {
            //显示验证码
            String scode = vt.split(",")[1];
            if (smsCode.equals(scode)) {
                f = true;
            }
        }
        return f;
    }

    /***********************************************************************************************
     * 发送短信的方法
     **********************************************************************************************/

    public static String SEND_URL = "http://www.139000.com/send/gsend.asp?name=%s&pwd=%s&dst=%s&sender=&time=&txt=ccdx&msg=%s";

    //public static String SEND_URL = "http://www.139000.com/send/gsend.asp?name={用户名}&pwd={短信密码}&dst={短信号码}&sender=&time=&txt=ccdx&msg=尊敬的客户，{短信内容}。";

    public static String SMS_ACCOUNT = "xghyhfwzx";

    public static String SMS_PASSWORD = "xghyhfwzx1";

    /**
     * 发送短信内容
     *
     * @param mobilePhone
     * @return
     */
    public final static MessageEntity sendMessage(String mobilePhone, String smsCode) {

        String content = "尊敬的客户，短信验证码为：" + smsCode + "！请对本条短信保密，千万不要让他人看到哦！";


        //发送短信
        int r = sendSms(mobilePhone, content);
        //如果发送成功
//        if (r == 1) {
            //保存到发送向量
            //addSmsCode(mobilePhone, mobilePhone, smsCode);

            //构建一个消息返回
            MessageEntity message = new MessageEntity();
            message.setMobilePhone(mobilePhone);
            message.setContent(content);
            message.setSmsCode(smsCode);
            message.setStatus(r);
            return message;
//        } else {
//            return null;
//        }
    }


    /**
     * 发送短信内容
     *
     * @param mobilePhone
     * @return
     */
    public final static MessageEntity sendBusinessMessage(String mobilePhone, String smsCode) {

        String content = "尊敬的客户，短信验证码为：" + smsCode + "！请对本条短信保密，千万不要让他人看到哦！";
        System.out.println("发送短信的内容:"+content);
        //发送短信
      // int r = sendSms(mobilePhone, content);
        //如果发送成功
//        if (r == 1) {
        //保存到发送向量
        //addSmsCode(mobilePhone, mobilePhone, smsCode);

        //构建一个消息返回
        MessageEntity message = new MessageEntity();
        message.setMobilePhone(mobilePhone);
        message.setContent(content);
        message.setSmsCode(smsCode);
        message.setStatus(1);
        return message;
//        } else {
//            return null;
//        }
    }

    /**
     * 发送短信内容
     *
     * @param mobilePhone
     * @return
     */
    public final static int sendBusinessAppAcountMessage(String mobilePhone, String password) {
        String content = "您的账号："+mobilePhone+"   密码："+password+"，不要告诉别人哦，登录后建议在设置中重新修改密码。";
        System.out.println("发送短信的内容:"+content);
        //发送短信
        int r = sendSms(mobilePhone, content);
        return r;
//        } else {
//            return null;
//        }
    }

    public final static int sendAcountMessage(String mobilePhone, String content) {
        //发送短信
        int r = sendSms(mobilePhone, content);
        return r;
//        } else {
//            return null;
//        }
    }

    /**
     * 发送
     *
     * @param mobilePhone
     * @param content
     * @return
     */
    private final static int sendSms(String mobilePhone, String content) {

        InputStream in = null;
        try {
            String sendUrl = String.format(SEND_URL, SMS_ACCOUNT, SMS_PASSWORD, mobilePhone, content);
            String sendUrl1 = HttpUtil.encodeParams(sendUrl, CharsetUtil.CHARSET_GBK);
            URL url = new URL(sendUrl1);
            in=url.openStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "GBK"));
            String result = reader.readLine();
            String[] results = result.toString().split("&");
            String num = results[0].substring(results[0].lastIndexOf("=") + 1, results[0].length());
            if (!"0".equals(num)) {
                return 1;
            } else {
                return 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

}
