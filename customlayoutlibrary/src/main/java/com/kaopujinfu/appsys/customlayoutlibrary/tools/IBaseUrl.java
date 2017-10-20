package com.kaopujinfu.appsys.customlayoutlibrary.tools;

/**
 * 接口地址定义
 * Created by zuoliji on 2017/3/28.
 */

public class IBaseUrl {

    /**
     * 用户接口
     */
    public static String USER = "login";
    /**
     * 查询车辆品牌列表  action
     */
    public static final String ACTION_LIST_BRAND = "LIST_BRAND";
    /**
     * 查询车辆子品牌列表  action
     */
    public static final String ACTION_LIST_SUB_BRAND = "LIST_SUB_BRAND";
    /**
     * 查询车辆型号列表  action
     */
    public static final String ACTION_LIST_MODEL = "LIST_MODEL";
    /**
     * 用户注销 action
     */
    public static final String ACTION_LOGOUT = "LOGOUT";

    /**
     * 库融
     */

    /**
     * 绑定监管器
     */
    public static String BANK_RF_DEV = "rf_dev";
    /**
     * 绑定监管器---》小圆盘的绑定 action
     */
    public static final String ACTION_BANK_BINDING = "BIND";
    /**
     * 绑定监管器---》获取小圆盘绑定列表 action
     */
    public static final String ACTION_BANK_BINDING_LISTS = "APP_LIST";
    /**
     * 文件上传
     */
    public static String UPLOAD_FILE = "upload_file";
    /**
     * 文档
     */
    public static String BOX_V5_CELL = "box_v5_cell";
    /**
     * 经销商
     */
    public static String COMPANY = "company";
    /**
     * 经销商action
     */
    public static String ACTION_COMPANY = "FIND_SHORT";
    public static String ACTION_COMPANY_GPS = "FIND_SHORT_GPS";

    /**
     * 盘库
     */
    public static String CHECK_TASK = "check_task";
    /**
     * sevlet根据VIN码查询车型
     */
    public static String CONF_CAR = "conf_car";

    /**
     * 盘库-任务列表
     */
    public static String MY_TASK_LIST = "MY_TASK_LIST";
    /**
     * 盘库 --任务所含车辆清单
     */
    public static String ITEM_IN_TASK = "APP_ITEM_IN_TASK";
    /**
     * 新建一个盘库任务
     */
    public static String NEW_TASK = "NEW";

    /**
     * 删除盘库任务
     */
    public static String DEL_TASK = "DELETE";
    /**
     * 盘库--提交预审
     */
    public static String SUBMIT_TASK = "SUBMIT";

    /**
     * 新建车辆--根据vin码查询车型
     */
    public static String ACTION_VIN_INFO = "VIN_INFO";

    /**
     * 新建车辆--车辆清单
     */
    public static String ACTION_APP_LIST = "APP_LIST";

    /**
     * 新建车辆
     */
    public static String URL_CAR = "car";
    /**
     * sevlet车辆帮标签
     */
    public static String URL_RFID = "rfid";
    /**
     * 新建车辆绑标签
     */
    public static String ACTION_BINDRFID = "BIND";

    /**
     * 车辆绑标签列表
     */
    public static String BIND_RFID_LIST = "BIND_RFID_LIST";
    /**
     * 我的-统计信息
     */
    public static String ACTION_APP_STAT_INFO = "APP_STAT_INFO";
    /**
     * 监管清单
     */
    public static String ACTION_LIST_CAR = "LIST_CAR";
    /**
     * 监管清单-详情
     */
    public static String ACTION_DLR_CAR = "DLR_CAR";

    /**
     * 申请清单 sevlet
     */
    public static String URL_LOAN_APP = "loan_app";
    /**
     * 申请清单
     */
    public static String ACTION_APP_LIST1 = "APP_LIST1";
    /**
     * 申请清单详情
     */
    public static String ACTION_APP_LIST2 = "APP_LIST2";
    /**
     * 贷款清单
     */
    public static String ACTION_APP_LIST3 = "APP_LIST3";
    /**
     * 贷款清单详情
     */
    public static String ACTION_APP_LIST4 = "APP_LIST4";

    /**
     * 柜子
     * */
    public static String CABINET_APP_LISTS="box_v5";
    public static String ACTION_CABINET_APP_LISTS="box_v5_cell";
    /**
     * 柜子列表
     * */
    public static String APP_BOX_LIST="APP_BOX_LIST";
    /**
     * 柜子详情
     * */
    public static String APP_BOX_CELL="APP_BOX_CELL";
    /**
     * 柜子自行监管说明
     * */
    public static String REG_MANUAL="REG_MANUAL";
    /**
     * 柜子自行监管开锁
     * */
    public static  String OPEN_MANUAL="OPEN_MANUAL";
    /**
     * 柜子自行监管停止
     * */
    public static String STOP_MANUAL="STOP_MANUAL";

    /**
     * 绑定GPS--URL
     * */
    public static String GPS_URL="gps_dev";
    /**
     * 绑定GPS---》GPS的绑定 action
     */
    public static final String ACTION_ADDGPS = "BIND";


}
