package com.kaopujinfu.appsys.customlayoutlibrary.tools;

/**
 * 全局常量定义
 * Created by zuoliji on 2017/3/28.
 */

public class IBase {
    public static final int RETAIL_NEGATIVE=-1;

    public static final int CONSTANT_ZERO=0;
    public static final int CONSTANT_ONE=1;
    public static final int CONSTANT_TWO=2;
    public static final int CONSTANT_THREE=3;
    public static final int CONSTANT_FOUR=4;
    public static final int CONSTANT_FIVE=5;
    public static final int CONSTANT_SEX=6;
    public static final int CONSTANT_SEVEN=7;
    public static final int CONSTANT_EIGTH=8;
    public static final int CONSTANT_NINE=9;
    public static final int CONSTANT_TEN=10;

    public static final int RETAIL_ZERO=0x100;
    public static final int RETAIL_ONE=0x101;
    public static final int RETAIL_TWO=0x102;
    public static final int RETAIL_THREE=0x103;
    public static final int RETAIL_FOUR=0x104;
    public static final int RETAIL_FIVE=0x105;
    public static final int RETAIL_SEX=0x106;
    public static final int RETAIL_SEVEN=0x107;
    public static final int RETAIL_EIGTH=0x108;
    public static final int RETAIL_NINE=0x109;
    public static final int RETAIL_TEN=0x1010;

    public static final int RETAIL_ELEVEN=0x1011;

    public static final int RESUTL_ZERO=0x200;
    public static final int RESUTL_ONE=0x201;
    public static final int RESUTL_TWO=0x202;
    public static final int RESUTL_THREE=0x203;
    public static final int RESUTL_FOUR=0x204;
    public static final int RESUTL_ELEVEN=0x2011;

    /**倒计时消息处理*/
    public static final int RETAIL_COUNTDOWN=0x1999;
    /** 蓝牙连接发送 */
    public static final int RETAIL_BLUETOOTH_OPEN=0x1998;
    /** 盘库 */
    public static final int RETAIL_CHECK=0x1997;

    /**邮箱验证字符串*/
    public static final String regMailbox="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    /**手机号验证字符串*/
    public static final String regTel="^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    /**身份证验证字符串*/
    public static final String IDcodeTel="^(\\d{15})|(\\d{17})([0-9]|X)$";

    /**判断网络异常页面是否是打开状态*/
    public static boolean isOpenNet=false;
    /**手机唯一标识*/
    public static String szImei="";
    /**用户ID保存*/
    public static String USERID="";
    /**用户sessionID保存*/
    public static String SESSIONID="";
    /**所属公司*/
    public static String COMPANY_CODE="";
    /**设置list每次取值得条数*/
    public static int limit=10;

    /** 动态表单控件类型 */
    public static final String TEXT="TEXT";
    public static final String LONG_TEXT="LONG_TEXT";
    public static final String SINGLE_CHOICE="SINGLE_CHOICE";
    public static final String MULTI_CHOICE="MULTI_CHOICE";
    public static final String DATE="DATE";
    public static final String TIME="TIME";
    public static final String DEV_GPS="DEV_GPS";
    public static final String MAP_POS="MAP_POS";
    public static final String NUMBER="NUMBER";
    public static final String SELETOR="SINGLE_SELECT";
    public static final String CAR_BRAND="CAR_BRAND";
    public static final String CAR_SUBBRAND="CAR_SUBBRAND";
    public static final String CAR_MODEL="CAR_MODEL";
    public static final String PROVINCE="PROVINCE";
    public static final String CITY="CITY";
    public static final String AREA="AREA";


    /** 盘库类别 */
    public static final String VINCODE="VIN";
    public static final String RFIDCODE="RFID";

    /** 数据库的名字 */
    public static final String BASE_DATE="thecar.db";

}
