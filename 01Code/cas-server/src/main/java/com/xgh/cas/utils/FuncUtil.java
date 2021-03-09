package com.xgh.cas.utils;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 函数-工具类
 *
 * @Description
 * @Date 2019/12/12 18:21
 * @Author JYL
 */
public class FuncUtil {

    /**
     * 常量零,用于比较
     */
    public final static BigDecimal ZERO = new BigDecimal(0);

    /**
     * 判断是否不为null 且 大于零
     *
     * @param v
     * @return
     */
    public final static boolean gtZero(BigDecimal v) {
        if (v == null) {
            return false;
        }
        return (v.compareTo(FuncUtil.ZERO) > 0);
    }

    /**
     * 生成UUID，其格式为：xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx(8-4-4-4-12)
     *
     * @return
     */
    public final static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成随机验证码
     *
     * @param length
     * @return
     */
    public final static String getRandCode(int... length) {
        /**
         * 验证码字符串
         */
        char[] p_code = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'U', 'V', 'W', 'X', 'Y',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'r', 'u', 'v', 'w', 'x', 'y',
                '2', '3', '4', '6', '8', '9'};
        //验证码生成
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        if (length.length > 0) {
            for (int i = 0; i < length[0]; i++) {
                sb.append(p_code[random.nextInt(p_code.length)]);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                sb.append(p_code[random.nextInt(p_code.length)]);
            }
        }

        return sb.toString();
    }

    /**
     * 返回一个从a到b中的随机整数
     *
     * @param a
     * @param b
     * @return
     */
    public final static int getRandInt(int a, int b) {
        Random rand = new Random();
        a = rand.nextInt((b - a) + 1) + a;
        return a;
    }

    /**
     * 逗号规整字符串
     * 去掉首尾的及中间的多个
     * from ",0,7,,18,1,63,5,31,28,"
     * to "0,7,18,1,63,5,31,28"
     *
     * @param str
     * @return
     */
    public final static String CleanComma(String str) {
        //先去掉两头空格
        str = CleanBlank(str);
        str = str.replaceAll("(^[,]*|[,]*$|(?<=[,])[,]+)", "");
        return str;
    }

    /**
     * 空格规整字符串
     * 去掉首尾的及中间的多个空格
     * from "0     18   28   11"
     * to "0 18 28 11"
     *
     * @param str
     * @return
     */
    public final static String CleanBlank(String str) {
        str = str.replaceAll("(^[ ]*|[ ]*$|(?<=[ ])[ ]+)", "");
        return str;
    }

    /**
     * 字符串去重
     *
     * @param ostr 包含分割字符的字符串
     * @param s    分割字符可以是空格，逗号，竖线
     * @return 新的包含分隔符的字符串
     */
    public final static String getPurStr(String ostr, String s) {
        StringBuffer sb = new StringBuffer();
        String[] nstrs = ostr.split(s);
        //竖线分割符号的处理
        if (s.equals("|")) {
            nstrs = ostr.split("\\|" + s);
        }
        for (String nstr : nstrs) {
            nstr = nstr.trim();
            if (!nstr.isEmpty() && sb.indexOf(nstr) < 0) {
                sb.append(nstr + s);
            }
        }
        if (sb.lastIndexOf(s) > -1)
            sb.deleteCharAt(sb.lastIndexOf(s));
        return sb.toString();
    }

    /**
     * 字符串去重
     *
     * @param ostr 包含分割字符的字符串
     * @param s    分割字符可以是空格，逗号，竖线
     * @param l    保留大于l的词
     * @return
     */
    public final static String getPurStr(String ostr, String s, int l) {
        StringBuffer sb = new StringBuffer();
        String[] nstrs = ostr.split(s);
        if (s.equals("|")) {
            nstrs = ostr.split("\\|" + s);
        }
        for (String nstr : nstrs) {
            nstr = nstr.trim();
            if (nstr.length() >= l && sb.indexOf(nstr) < 0) {
                sb.append(nstr + s);
            }
        }
        if (sb.lastIndexOf(s) > -1)
            sb.deleteCharAt(sb.lastIndexOf(s));
        return sb.toString();
    }

    /**
     * 字符串去重
     *
     * @param ostr 包含分割字符的字符串
     * @param s    分割字符可以是空格，逗号，竖线
     * @param l    保留大于l的词
     * @param n    丢弃大于n的
     * @return
     */
    public final static String getPurStr(String ostr, String s, int l, int n) {
        int i = 0;
        StringBuffer sb = new StringBuffer();
        String[] nstrs = ostr.split(s);
        if (s.equals("|")) {
            nstrs = ostr.split("\\|" + s);
        }
        for (String nstr : nstrs) {
            nstr = nstr.trim();
            if (nstr.length() >= l && sb.indexOf(nstr) < 0) {
                i++;
                sb.append(nstr + s);
            }
            if (i > n) break;
        }
        if (sb.lastIndexOf(s) > -1)
            sb.deleteCharAt(sb.lastIndexOf(s));
        return sb.toString();
    }

    /**
     * 统计子字符s在y中的出现的次数
     *
     * @param s
     * @param y
     * @return
     */
    public final static int CountSubStr(String s, String y) {
        int count = 0;
        Pattern pat = Pattern.compile("(," + s + ",)");
        Matcher matcher = pat.matcher("," + y + ",");
        while (matcher.find()) {
            count = count + 1;
        }
        return count;
    }

    /**
     * 按照sub截取并返回第n个逗号之前的str
     * 分割符sub可以是逗号、空格、竖线
     *
     * @param sub
     * @param str
     * @param n   丢弃大于n的
     * @return
     */
    public final static String getSubString(String sub, String str, int n) {
        int i = 0;
        StringBuffer sb = new StringBuffer();
        String[] nstrs = str.split(sub);
        if (sub.equals("|")) {
            nstrs = str.split("\\\\" + sub);
        }
        for (String nstr : nstrs) {
            i++;
            sb.append(nstr + sub);
            if (i > n) break;
        }
        if (sb.lastIndexOf(sub) > -1)
            sb.deleteCharAt(sb.lastIndexOf(sub));
        return sb.toString();
    }

    /**
     * 可以替换特殊字符的替换方法,replaceAll只能替换普通字符串,含有特殊字符的不能替换
     *
     * @param strSource 用户输入的字符串
     * @param strFrom   数据库用需要替换的字符
     * @param strTo     需要替换的字符替换为该字符串
     * @return
     */
    public final static String replace(String strSource, String strFrom, String strTo) {
        if (strSource == null) return null;
        int i = 0;
        if ((i = strSource.indexOf(strFrom, i)) >= 0) {
            char[] cSrc = strSource.toCharArray();
            char[] cTo = strTo.toCharArray();
            int len = strFrom.length();
            StringBuffer buf = new StringBuffer(cSrc.length);
            buf.append(cSrc, 0, i).append(cTo);
            i += len;
            int j = i;
            while ((i = strSource.indexOf(strFrom, i)) > 0) {
                buf.append(cSrc, j, i - j).append(cTo);
                i += len;
                j = i;
            }
            buf.append(cSrc, j, cSrc.length - j);
            return buf.toString();
        }
        return strSource;
    }

    /**
     * 搜索到的字符串标红
     *
     * @param content
     * @param sousuoSeg
     * @return
     */
    public final static String replace(String content, String[] sousuoSeg) {
        for (String s : sousuoSeg) {
            content = content.replaceAll(s, "<span class=\"ftclr1\">" + s + "</span>");
        }
        return content;
    }

    /**
     * 获取一个double类型数值的百分比
     * example: FormatPercent(0.33333333,2) = 33%
     *
     * @param number
     * @param newValue
     * @return
     */
    public final static String FormatPercent(double number, int newValue) {
        java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(newValue);
        return nf.format(number);
    }

    /**
     * 两个数组pids,quantitys有对应的关系，
     * 给一个pid 在pids内找到对应的数组下标，并利用这个下标找到对应的quantitys内的值
     *
     * @param pid
     * @param pids
     * @param quantitys
     * @return
     */
    public static Integer getQuantity(Long pid, Long[] pids, Integer[] quantitys) {
        Integer r = -1;
        for (int i = 0; i < pids.length; i++) {
            if (pid.equals(pids[i])) {
                r = quantitys[i];
                break;
            }
        }
        return r;
    }

    /**
     * 长整型数组转换成逗号分割的字符串
     *
     * @param as
     * @return
     */
    public final static String Array2Str(Long[] as) {
        StringBuffer str = new StringBuffer();
        for (Long a : as) {
            str.append(a + ",");
        }
        return CleanComma(str.toString());
    }

    /**
     * 逗号分割的字符串转为Long[]数组
     *
     * @param str
     */
    public final static Long[] Str2LongArray(String str) {
        String[] rstrs = str.split(",");
        Long[] result = new Long[rstrs.length];
        for (int i = 0; i < rstrs.length; i++) {
            result[i] = Long.parseLong(rstrs[i]);
        }
        return result;
    }

    /**
     * 逗号分割的字符串转换成List<Long>
     *
     * @param str
     * @return
     */
    public final static List<Long> Str2List(String str) {
        return Array2List(Str2LongArray(str));
    }

    /**
     * 整型数组转List
     *
     * @param idsa
     * @return
     */
    public final static List<Long> Array2List(Long[] idsa) {
        if (!CheckUtil.isLongArray(idsa)) {
            return null;
        }

        List<Long> idsl = new LinkedList<>();
        for (Long a : idsa) {
            idsl.add(a);
        }
        return idsl;
    }


    /**
     * 整形数组转逗号分隔字符串
     *
     * @param as
     * @return
     */
    public final static String Array2Str(Integer[] as) {
        StringBuffer str = new StringBuffer();
        for (Integer a : as) {
            str.append(a + ",");
        }
        return CleanComma(str.toString());
    }

    /**
     * 逗号分割的字符串转为Long[]数组
     *
     * @param str
     */
    public final static Integer[] Str2IntArray(String str) {
        String[] rstrs = str.split(",");
        Integer[] result = new Integer[rstrs.length];
        for (int i = 0; i < rstrs.length; i++) {
            result[i] = Integer.parseInt(rstrs[i]);
        }
        return result;
    }

    /**
     * 整型数组转List
     *
     * @param idsa
     * @return
     */
    public final static List<Integer> Array2List(Integer[] idsa) {
        if (!CheckUtil.isIntegerArray(idsa)) {
            return null;
        }

        List<Integer> idsl = new LinkedList<>();
        for (Integer a : idsa) {
            idsl.add(a);
        }
        return idsl;
    }

    /**
     * 字符串数组转换逗号分割的字符串
     *
     * @param as
     * @return
     */
    public final static String Array2Str(String[] as) {
        StringBuffer str = new StringBuffer();
        for (String a : as) {
            str.append(a + ",");
        }
        return CleanComma(str.toString());
    }

    /**
     * List<Long>集合转换成逗号分隔的字符串
     *
     * @param ls
     * @return
     */
    public final static String ListInteger(List<Integer> ls) {
        Collections.sort(ls);
        StringBuffer str = new StringBuffer();
        for (Integer a : ls) {
            str.append(a + ",");
        }
        return getPurStr(str.toString(), ",");
    }

    /**
     * List<Long>集合转换成逗号分隔的字符串
     *
     * @param ls
     * @return
     */
    public final static String List2Str(List<Long> ls) {
        Collections.sort(ls);
        StringBuffer str = new StringBuffer();
        for (Long a : ls) {
            str.append(a + ",");
        }
        return getPurStr(str.toString(), ",");
    }

    /**
     * List<Long>集合转换成逗号分隔的字符串 方法
     *
     * @param ls
     * @return
     */
    public final static String ListStr(List<Long> ls) {
        StringBuffer str = new StringBuffer();

        for (int i = 0; i < ls.size(); i++) {
            str.append(ls.get(i)).append(",");
        }
        return getPurStr(str.toString(), ",");
    }

    /**
     * 检索一个整数是否在数组内，如果在返回数组中的位置，否则返回0;
     *
     * @param iarray
     * @param s
     * @return
     */
    public final static Integer findIntArray(Integer[] iarray, Integer s) {
        int r = 0;
        if (iarray != null && iarray.length > 0)
            for (int i = 0; i < iarray.length; i++) {
                if (s == iarray[i]) {
                    r = i + 1;
                    break;
                }
            }
        return r;
    }

    /**
     * 获取数值
     * example:FormatNumber("50%") = 0.5
     *
     * @param value
     * @return
     */
    public final static double FormatNumber(String value) {
        double n = 0;
        java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        try {
            n = nf.parse(value).doubleValue();
        } catch (ParseException e) {
        }
        return n;
    }

    /**
     * double转换成String并保留两位小说
     *
     * @param value
     * @return
     */
    public final static String DoubleToString(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }


    /**
     * 把ip与端口及后面的整数转换成一个长整型
     *
     * @param ipStr
     * @return
     * @author G/2016-1-12 上午9:13:26
     */
    public final static Long ipStr2Long(String ipStr) {
        Long num = new Long(0);
        String[] ipp = ipStr.split(":");
        // ip地址转换长整型
        Long ipnum = ip2Long(ipp[0]);
        // 端口号转换4位长整型
        Long port = Long.parseLong(ipp[1]);

        // 防区号转换2位长整型
        Long number = Long.parseLong(ipp[2]);
        if (number < 10)
            number *= 10;
        // 合成一个新的长整型
        num = ipnum * 10000000 + port * 100 + number;
        return num;
    }

    /**
     * 把字符串ip地址转换成长整型
     *
     * @param ip
     * @return
     */
    public final static Long ip2Long(String ip) {
        if (ip.indexOf("0:0:0:0:0") > -1) {
            return 1L;
        }
        String[] ips = ip.split("\\.");
        Long w = Long.parseLong(ips[0]);
        Long x = Long.parseLong(ips[1]);
        Long y = Long.parseLong(ips[2]);
        Long z = Long.parseLong(ips[3]);
        Long ipnum = 16777216 * w + 65536 * x + 256 * y + z;
        return ipnum;
    }

    /**
     * 把长整型的ip地址转换成字符型的IP地址
     *
     * @param ipnum
     * @return
     */
    public final static String Long2ip(Long ipnum) {
        String ip = "";
        String w = String.valueOf((ipnum / 16777216) % 256);
        String x = String.valueOf((ipnum / 65536) % 256);
        String y = String.valueOf((ipnum / 256) % 256);
        String z = String.valueOf((ipnum) % 256);
        ip = w + "." + x + "." + y + "." + z;
        return ip;
    }


    /**
     * 获取本机Ip
     * 通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
     * 获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    public final static String getLocalIp() {
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return ip;
    }

    /**
     * 手机号中间4位加星
     *
     * @param phone
     * @return
     */
    public static String phoneEncryption(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 匿名
     *
     * @param name
     * @return
     */
    public static String getAnonymous(String name) {
        char[] nameChar = name.toCharArray();
        if (nameChar.length == 1) {
            return "*";
        } else if (nameChar.length == 2) {
            nameChar[1] = '*';
        } else if (nameChar.length > 1 && nameChar.length < 10) {
            for (int i = 0; i < nameChar.length; i++) {
                if (i > 0 && i < nameChar.length - 1) {
                    nameChar[i] = '*';
                }
            }
        } else {
            for (int i = 0; i < nameChar.length; i++) {
                if (i > 2 && i < nameChar.length - 4) {
                    nameChar[i] = '*';
                }
            }
        }
        return String.valueOf(nameChar);
    }

    /**
     * 取得 www.abc.com 的IP地址
     *
     * @param dname
     * @return
     */
    public final static String domain2Ip(String dname) {
        String ipaddress = "";
        try {
            InetAddress myServerIp = InetAddress.getByName(dname);
            ipaddress = myServerIp.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ipaddress;
    }

    /**
     * 判断字符串中第一个数字位置
     *
     * @param str
     * @return
     */
    public static Integer firstNumberIndex(String str) {
        if (StringUtils.isEmpty(str)) {
            return -1;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取字符串中的数字
     *
     * @param strNo
     * @return
     */
    public static Long getIdFromStr(String strNo) {
        if (CheckUtil.isEmpty(strNo)) {
            return null;
        }
        Integer b = firstNumberIndex(strNo);
        if (b < 0) {
            return null;
        }

        return Long.parseLong(strNo.substring(b, strNo.length()));
    }

    /**
     * 生成单位代码
     *
     * @param v
     * @return
     */
    public final static String getUnitNo(String v) {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

}
