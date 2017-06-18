package com.kaopujinfu.appsys.customlayoutlibrary.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuli on 2015/11/27.
 */
public class DateUtil {


    /**
     * 浣跨敤鐢ㄦ埛鏍煎紡鎻愬彇瀛楃涓叉棩鏈?
     *
     * @param strDate 鏃ユ湡瀛楃涓?
     * @param pattern 鏃ユ湡鏍煎紡
     * @return
     */

    public static Date parse(String strDate, String pattern) {

        if (TextUtil.isEmpty(strDate)) {
            return null;
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 浣跨敤鐢ㄦ埛鏍煎紡鏍煎紡鍖栨棩鏈?
     *
     * @param date    鏃ユ湡
     * @param pattern 鏃ユ湡鏍煎紡
     * @return
     */

    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyyy-MM-dd HH：mm"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDateYYYYMMDDHHMMM(long data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(data));
    }

    public static String fileSimpleDateYYYYMMDDHHMMMSS(long data) {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(data));
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyyy-MM-dd HH：mm:ss"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDateYYYYMMDDHHMMMSS(long data) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(data));
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyyy-MM-dd"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDateYYYYMMDD(long data) {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(data));
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyMM"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDate(long data) {
        return new SimpleDateFormat("yy:MM").format(new Date(data));
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyMM"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDateFromString(String sData) {
        if (sData == null) {
            return "未知";
        }
        long data = Long.parseLong(sData);
        return new SimpleDateFormat("MM-dd HH:mm").format(new Date(data));
    }

    /**
     * 获取一个时间的字符串，格式如下:"yyMM"
     *
     * @return 返回如：06:09
     */
    public static String getSimpleDateYYMMDD(String sData) {
        long data;
        if (sData == null) {
            return "未知";
        }
        try {
            data = Long.parseLong(sData);
        } catch (Exception e) {
            return sData;
        }
        return new SimpleDateFormat("yy:MM-dd").format(new Date(data));
    }

    /**
     * 获取两个时间差
     */
    public static long getTwoDate(String str1, String str2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = df.parse(str1);
            Date date2 = df.parse(str2);
            LogUtils.debug("date2.getTime()-date1.getTime()===" + (date2.getTime() - date1.getTime()));
            return date2.getTime() - date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtils.debug("进入了......");
        }
        return 0;
    }

    /* 时间string 转换日期 */
    public static Date stringToDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = null;
        try {
            dateValue = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateValue;
    }

    public static String calculateTime(Long recordingtime) {
        long minute = recordingtime / 60;
        if (minute < 60) {
            return minute + "分钟";
        } else if (minute == 60) {
            return "1小时";
        } else {
            long hour = minute / 60;
            minute = minute % 60;
            return hour + "小时" + minute + "分钟";
        }
    }

    public static String calculateTime(int recordingtime) {
        int minute = recordingtime / 60;
        //少于60分钟
        if (minute == 0) {
            if (recordingtime <= 9) {
                return "00:0" + recordingtime;
            }
            return "00:" + recordingtime;
        }
        int second = recordingtime % 60;
        if (minute <= 9) {
            if (second <= 9) {
                return "O" + minute + ":" + "0" + second;
            } else {
                return "O" + minute + ":" + second;
            }

        } else {
            if (second <= 9) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }

    public static String calculateTime1(long duration) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");//初始化Formatter的转换格式。
        LogUtils.debug(" formatter.format(duration)=" + formatter.format(duration));
        return formatter.format(duration);
    }

    /*
* 毫秒转化
*/
    public static String formatTime(long ms) {
        LogUtils.debug("formatTime--" + ms);
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strMinute + " 分钟 " + strSecond + " 秒";
    }

    /**
     * 根据毫秒得到多少天
     */
    public static long formatTimeDay(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = ms / dd;
        return day;
    }

    /**
     * 判断距离现在多少年月
     */
    public static String howMach(String strDate) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date d1 = df.parse(strDate);
            Date d2 = new Date(System.currentTimeMillis());//你也可以获取当前时间
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            LogUtils.debug("" + days + "天" + hours + "小时" + minutes + "分");
            long years = days / 365;
            long ds = days % 356;
            long mos = ds / 30;
            LogUtils.debug("" + years + "年零" + mos + "天");
            if (years != 0)
                return years + "年零" + mos + "个月";
            else if (mos != 0)
                return mos + "个月";
            else
                return "不足一个月";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
