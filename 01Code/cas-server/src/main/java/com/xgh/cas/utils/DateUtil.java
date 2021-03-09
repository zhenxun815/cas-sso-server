package com.xgh.cas.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    public final static SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
    public final static SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    public final static SimpleDateFormat sdfSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public final static SimpleDateFormat sdfSeconds = new SimpleDateFormat("yyyyMMddHHmmss");
    public final static SimpleDateFormat sdfMill = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    public final static SimpleDateFormat sdfMills = new SimpleDateFormat("yyyyMMddHHmmss");
    public final static SimpleDateFormat sdfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public final static DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * 根据日期获取SimpleDateFormat类型信息
     *
     * @param sdf:SimpleDateFormat对象
     * @param date:日期
     */
    public static String getFormatDate(SimpleDateFormat sdf, Object date) {
        try {
            if (date != null && !"".equals(date)) {
                if (date instanceof Date) {
                    return sdf.format(date);
                } else if (date instanceof String) {
                    return sdf.format(sdfMill.parse(date.toString()));
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 根据日期获取yyyy格式
     *
     * @param date:日期
     */
    public static String getYear(Object date) {
        return getFormatDate(sdfYear, date);
    }

    /**
     * 根据当前日期获取yyyy格式
     */
    public static String getCurrentYear() {
        return sdfYear.format(new Date());
    }

    /**
     * 根据日期获取yyyy-MM-dd格式
     *
     * @param date:日期
     */
    public static String getDay(Object date) {
        return getFormatDate(sdfDay, date);
    }

    /**
     * 根据当前日期获取yyyy-MM-dd格式
     */
    public static String getCurrentDay() {
        return sdfDay.format(new Date());
    }

    /**
     * 根据日期获取yyyyMMdd格式
     *
     * @param date:日期
     */
    public static String getDays(Object date) {
        return getFormatDate(sdfDays, date);
    }

    /**
     * 根据当前日期获取yyyyMMdd格式
     */
    public static String getCurrentDays() {
        return sdfDays.format(new Date());
    }

    /**
     * 根据日期获取yyyy-MM-dd HH:mm:ss格式
     *
     * @param date:日期
     */
    public static String getSecond(Object date) {
        return getFormatDate(sdfSecond, date);
    }

    /**
     * 根据当前日期获取yyyy-MM-dd HH:mm:ss格式
     */
    public static String getCurrentSecond() {
        return sdfSecond.format(new Date());
    }

    /**
     * 根据日期获取yyyyMMddHHmmss格式
     *
     * @param date:日期
     */
    public static String getSeconds(Object date) {
        return getFormatDate(sdfSeconds, date);
    }

    /**
     * 根据当前日期获取yyyyMMddHHmmss格式
     */
    public static String getCurrentSeconds() {
        return sdfSeconds.format(new Date());
    }

    /**
     * 根据日期获取yyyy-MM-dd HH:mm:ss.SSS格式
     *
     * @param date:日期
     */
    public static String getMill(Object date) {
        return getFormatDate(sdfMill, date);
    }

    /**
     * 根据当前日期获取yyyy-MM-dd HH:mm:ss.SSS格式
     */
    public static String getCurrentMill() {
        return sdfMill.format(new Date());
    }

    /**
     * 根据日期获取yyyyMMddHHmmssSSS格式
     *
     * @param date:日期
     */
    public static String getMills(Object date) {
        return getFormatDate(sdfMills, date);
    }

    /**
     * 根据当前日期获取yyyyMMddHHmmssSSS格式
     */
    public static String getCurrentMills() {
        return sdfMills.format(new Date());
    }

    /**
     * 字符串转日期
     *
     * @param day
     * @return
     * @author G/2015-1-15 下午2:33:02
     */
    public static Date str2Date(String day) {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = (Date) formatter.parse(day);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 字符串转日期时间
     *
     * @param day
     * @return
     * @author G/2015-1-16 上午8:14:57
     */
    public static Date str2DateTime(String day) {
        Date date = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = (Date) formatter.parse(day);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 日期转字符串
     *
     * @param d
     * @return yyyy-MM-dd
     * @author G/2015-1-15 下午2:32:54
     */
    public static String date2Str(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    /**
     * 日期转字符串
     *
     * @param d
     * @return yyyy-MM-dd HH:mm:ss
     * @author G/2015-1-15 下午2:32:54
     */
    public static String dateTime2Str(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);
    }

    /**
     * 获取当前日期时间
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 时间戳转日期
     *
     * @param time
     * @author G/2018/6/14 12:19
     */
    public static Date getDate(long time) {
        return new Date(time);
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     * @author G/2015-1-16 上午8:15:56
     */
    public static Date dateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        return now.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     * @author G/2015-1-16 上午8:16:02
     */
    public static Date dateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到几分钟后的时间
     *
     * @param d
     * @param min 加的分钟数
     * @return
     * @author G/2015-1-16 上午8:16:02
     */
    public static Date minutesAfter(Date d, int min) {
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, min);
        return nowTime.getTime();
    }

    /**
     * 得到当天的星期
     *
     * @return
     * @author G/2015-1-16 上午8:16:10
     */
    public final static int week() {
        Calendar now = Calendar.getInstance();
        return (now.get(Calendar.DAY_OF_WEEK) - 1);
    }

    /**
     * 得到当天的星期
     *
     * @return
     * @author G/2015-1-16 上午8:16:10
     */
    public final static int day() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前年份 2015
     *
     * @return 2015
     * @author G/2015-1-16 上午8:16:16
     */
    public final static int year() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }


    /**
     * 获取当前月整数形式
     *
     * @param
     * @return
     * @author:G/2017年10月31日
     */
    public final static int month() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * 得到某年月的第一天
     *
     * @param year
     * @param month
     * @return
     * @author G/2015-1-16 上午8:17:56
     */
    public final static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    /**
     * 得到某年月的最后一天
     *
     * @param year
     * @param month
     * @return
     * @author G/2015-1-16 上午8:25:01
     */
    public final static String getLastDayOfMonth(int year, int month) {
        String monthStr = "," + month;
        //这里的返回值要区分1,3,5,7,8,10,12
        if (",1,3,5,7,8,10,12".indexOf(monthStr) >= 0) {
            return year + "-" + month + "-" + 31;
        } else if (",4,6,9,11".indexOf(monthStr) >= 0) {
            return year + "-" + month + "-" + 30;
        } else {
            //判断闰年
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                return year + "-" + month + "-" + 29;
            } else {
                return year + "-" + month + "-" + 28;
            }
        }
    }

    /**
     * 得到当前年份和月份2012-05
     *
     * @return
     * @author G/2015-1-16 上午8:16:24
     */
    public final static String yearMonth() {
        final SimpleDateFormat dmonth = new SimpleDateFormat("yyyy-MM");
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1); // first day of month
        return dmonth.format(c.getTime());
    }

    /**
     * 得到当前年份和月份的第一天2012-05-01
     *
     * @return
     * @author G/2015-1-16 上午8:16:29
     */
    public final static String yearMonthDay1st() {
        final SimpleDateFormat dm = new SimpleDateFormat("yyyy-MM-dd");
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1); // first day of month
        return dm.format(c.getTime());
    }

    /**
     * 得到当前年份和月份天2012-05-01
     *
     * @return
     * @author G/2015-1-16 上午8:16:33
     */
    public final static String yearMonthDay() {
        final SimpleDateFormat dm = new SimpleDateFormat("yyyy-MM-dd");
        return dm.format(now());
    }

    /**
     * 得到当前4位年份2位月份2位天20120501
     *
     * @return
     * @author G/2015-1-16 上午8:16:33
     */
    public final static String yyyymmdd() {
        final SimpleDateFormat dm = new SimpleDateFormat("yyyyMMdd");
        return dm.format(now());
    }

    /**
     * 得到当前2位月份2位天0501
     *
     * @return
     * @author G/2015-1-16 上午8:16:33
     */
    public final static String mmdd() {
        final SimpleDateFormat dm = new SimpleDateFormat("MMdd");
        return dm.format(now());
    }

    /**
     * 得到当前6位 时分秒
     *
     * @return
     * @author G/2015-1-16 上午8:16:33
     */
    public final static String hhmmss() {
        final SimpleDateFormat dm = new SimpleDateFormat("HHmmss");
        return dm.format(now());
    }

    /**
     * 得到当前年、月、日 时、分、秒 2012-05-01 8:16:39
     *
     * @return "yyyy-MM-dd HH:mm:ss"
     * @author G/2015-1-16 上午8:16:39
     */
    public final static String yearMonthDayTime() {
        final SimpleDateFormat dm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dm.format(now());
    }

    /**
     * 得到最简年月日时分秒表示
     *
     * @return "yMdHms"
     * @author G/2016年5月13日 下午3:46:32
     */
    public final static String yearMonthDayTimeShort() {
        final SimpleDateFormat dm = new SimpleDateFormat("yMdHms");
        return dm.format(now());
    }

    /**
     * 得到最简年月日表示
     *
     * @return "yMd"
     * @author G/2016年5月13日 下午3:46:32
     */
    public final static String yearMonthDayShort() {
        final SimpleDateFormat dm = new SimpleDateFormat("yMd");
        return dm.format(now());
    }

    /**
     * 得到当前时分秒
     *
     * @return "HH:mm:ss"
     * @author G/2015-1-16 上午8:16:44
     */
    public final static String time() {
        final SimpleDateFormat dm = new SimpleDateFormat("HH:mm:ss");
        return dm.format(now());
    }

    /**
     * 得到当前时分
     *
     * @return "HH:mm"
     * @author G/2015-1-16 上午8:16
     */
    public final static String timeMinute() {
        final SimpleDateFormat dm = new SimpleDateFormat("HH:mm:ss");
        return dm.format(now());
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     * @param date2
     * @return
     * @author G/2015-1-16 上午8:16:50
     */
    public final static long compareDate(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

    /**
     * 比较两个日期时间的大小
     *
     * @param time1
     * @param time2
     * @return
     * @author G/2015-1-16 上午8:16:50
     */
    public final static long compareTime(String time1, String time2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long l = 0;
        try {
            Date now = df.parse(time1);
            Date date = df.parse(time2);
            l = now.getTime() - date.getTime();
        } catch (ParseException e) {
        }
        return l;
    }

    /**
     * 比较一个日期时间和当前时间相差的毫秒数
     *
     * @param dateTime
     * @return
     * @author G/2016年8月8日 下午3:54:50
     */
    public final static long compareTimeNow(Date dateTime) {
        long l = 0;
        l = now().getTime() - dateTime.getTime();
        return l;
    }

    /**
     * 得到当前年份和月份天2012-05-01
     *
     * @param day
     * @return
     * @author G/2015-1-16 上午8:16:58
     */
    public final static String yearMonthDayTime(int day) {
        final Calendar now = Calendar.getInstance();
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now.getTime());
    }

    /**
     * 得到某年月的第一天
     *
     * @param year
     * @param month
     * @return
     * @author G/2015-1-16 上午8:17:56
     */
    public final static String firstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    /**
     * 获取一年后的时间
     * @param date
     * @return
     */
    public final static Date delayYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, +1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 得到某年月的最后一天
     *
     * @param year
     * @param month
     * @return
     * @author G/2015-1-16 上午8:25:01
     */
    public final static String lastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
    }

    /**
     * 得到某年月的每一天
     *
     * @param year
     * @param month
     * @return
     * @author G/2015-1-16 上午8:24:43
     */
    public final static String[] daysOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        String[] days = new String[end];
        for (int i = 0; i < end; i++) {
            cal.set(Calendar.DAY_OF_MONTH, i + 1);
            days[i] = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        }
        return days;
    }

    /**
     * 某年的月份显示
     *
     * @param
     * @return
     * @author:G/2018年1月19日
     */
    public final static String[] monthsOfYear(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        int end = cal.getActualMaximum(Calendar.MONTH) + 1;
        String[] months = new String[end];
        for (int i = 0; i < end; i++) {
            cal.set(Calendar.MONTH, i);
            months[i] = new SimpleDateFormat("yyyy-MM").format(cal.getTime());
        }
        return months;
    }

    /**
     * 计算两个日期之间相差的分钟数
     *
     * @param
     * @return
     * @author:G/2017年9月1日
     */
    public final static long minutesBetween(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (1000 * 60);
    }

    /**
     * 计算两个日期之间相差的小时数
     *
     * @param
     * @return
     * @author:G/2017年9月23日
     */
    public final static long hoursBetween(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (1000 * 3600);
    }

    /**
     * 计算两个日期之间相差的天数
     */
    public final static long daysBetween(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) / (1000 * 3600 * 24);
    }

    /**
     * 计算两个日期之间月份差
     */
    public final static int monthsBetween(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 date1 = 2015-8-16  date2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 分钟数转换成小时，超过1秒钟也算1小时
     *
     * @param
     * @return
     * @author:G/2018年1月19日
     */
    public final static long minutes2hours(long minutes) {
        return (minutes % 60 > 0 ? (minutes / 60 + 1) : (minutes / 60));
    }

    public final static long hours2days(long hours) {
        return (hours / 24);
    }

    /**
     * 获取中文周
     *
     * @param
     * @return
     * @author:G/2017年9月27日
     */
    public final static String chnWeek(int w) {
        String r = "";
        switch (w) {
            case 1:
                r = "一";
                break;
            case 2:
                r = "二";
                break;
            case 3:
                r = "三";
                break;
            case 4:
                r = "四";
                break;
            case 5:
                r = "五";
                break;
            case 6:
                r = "六";
                break;
            case 7:
                r = "日";
                break;
        }
        return r;
    }

    /**
     * Description: 判断一个时间是否在一个时间段内 </br>
     *
     * @param nowTime   当前时间 </br>
     * @param startTime 开始时间 </br>
     * @param endTime   结束时间 </br>
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        return date.after(begin) && date.before(end);
    }

	public static void main(String args[]) throws Exception {
		//三级级推广百分比费率
		//System.out.println(DateUtil.dateAfter(new Date(),3));
		//加载需要拆分的PDF文档
        //System.out.println(DateUtil.compareDate(DateUtil.str2DateTime("2021-02-15 00:00:00"),DateUtil.str2DateTime("2021-02-14 00:00:00"))>0);
        DateUtil.getMonthFirstAndLastDay();
	}

        /**
         * 获取当前日期所在的周的周一到周五
         * @return
         */
    public static Map<String,String> getWeekDate() {
        Map<String,String> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if(dayWeek==1){
            dayWeek = 8;
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);
        System.out.println("所在周星期一的日期：" + weekBegin);
        System.out.println("------：" + cal.getFirstDayOfWeek());
        map.put("周一", weekBegin);
        for (int i = 1; i < 7; i++) {
            cal.add(Calendar.DATE, 1);
            Date sundayDate = cal.getTime();
            String weekEnd = sdf.format(sundayDate);
            map.put("周"+chnWeek(i+1), weekEnd);
        }
        return map;
    }

    public static Map<String,String> getWeekFirstAndLast() {
        Map<String,String> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if(dayWeek==1){
            dayWeek = 8;
        }
        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);
        System.out.println("所在周星期一的日期：" + weekBegin);
        System.out.println("------：" + cal.getFirstDayOfWeek());

        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);
        System.out.println("所在周星期日的日期：" + weekEnd);

        map.put("mondayDate", weekBegin);
        map.put("sundayDate", weekEnd);

        return map;
    }

    /**
     * 获取当前日期所造月的每一天
     * @return
     */
    public static String [] getMonthFirstAndLastDay() {
        Map<String,String> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        // 获取前月的第一天
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = sdf.format(cal.getTime());
        System.out.println("所在月第一天：" + firstday);
        // 获取前月的最后一天
        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.MONTH, 1);
        cal2.set(Calendar.DAY_OF_MONTH, 0);
        int day = cal2.get(Calendar.DATE);//日
        System.out.println("所在月最后一天的天：" +day);
        String lastday = sdf.format(cal2.getTime());
        System.out.println("所在月最后一天：" + lastday);
        String [] days = new String[day];
        days[0] = firstday;
        for (int i = 1; i < day; i++) {
            cal.add(Calendar.DATE, 1);
            Date date = cal.getTime();
            String everyDay = sdf.format(date);
            days[i] = everyDay;
            System.out.println("所在月的第"+(i+1)+"天：" + everyDay);
        }
        System.out.println("所在月的天:"+days.toString());
        return days;
    }

}
