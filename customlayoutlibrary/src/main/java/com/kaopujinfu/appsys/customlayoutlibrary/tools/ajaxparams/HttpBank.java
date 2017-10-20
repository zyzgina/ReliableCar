package com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.VinCodeBean;
import com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils.AjaxParams;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;

import net.tsz.afinal.FinalDb;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 库融
 * Created by 左丽姬 on 2017/4/10.
 */

public class HttpBank {
    private static Context context;
    private static HttpBank httpBank;
    private static BankAjaxParams bankAjaxParams;

    public static HttpBank getIntence(Context context) {
        HttpBank.context = context;
        HttpBank.bankAjaxParams = new BankAjaxParams(context);
        if (httpBank == null)
            httpBank = new HttpBank();
        return httpBank;
    }


    /**登录*/
    /**
     * 登录接口提交
     *
     * @param usernme  用户名
     * @param userpass 密码
     * @param callBack 回调函数
     */
    public void login(String usernme, String userpass, String userCode, final CallBack<Object> callBack) {
        if (GeneralUtils.isEmpty(usernme)) {
            IBaseMethod.showToast(context, "请填写用户名", IBase.RETAIL_TWO);
            callBack.onFailure(0, "请填写用户名");
            return;
        }
        if (GeneralUtils.isEmpty(userpass)) {
            IBaseMethod.showToast(context, "请填写密码", IBase.RETAIL_TWO);
            callBack.onFailure(0, "请填写密码");
            return;
        }
        AjaxParams params = bankAjaxParams.login(usernme, userpass, userCode);
        IBaseMethod.post(context, IBaseUrl.USER, params, callBack);
    }

    /**
     * 获取个人信息、获取个人进件数量,进件首页为你推荐
     *
     * @param action
     */
    public void getAction(String action, String url, CallBack<Object> callBack) {
        AjaxParams params = bankAjaxParams.getAction(action);
        IBaseMethod.post(context, url, params, callBack);
    }


    /**
     * 修改密码
     *
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @param callBack    回调函数
     */
    public void updatePassword(String oldPassword, String newPassword, CallBack<Object> callBack) {
        AjaxParams params = bankAjaxParams.updatePassword(oldPassword, newPassword);
        IBaseMethod.post(context, IBaseUrl.USER, params, callBack);
    }

    /**
     * 获取token
     *
     * @param callBack
     */
    public void getToken(CallBack callBack) {
        IBaseMethod.post(context, IBaseUrl.UPLOAD_FILE, bankAjaxParams.getTokenAjax(), callBack);
    }

    /**
     * 上传七牛云成功后通知服务器
     *
     * @param bizType  业务类型名称，比如：监管器绑定、文档绑定、车辆外观
     * @param bizId    业务编号，比如一个任务号，或者一部车辆的VIN码等
     * @param storeKey 存储在七牛云上的KEY
     * @param fileName 文件短名称
     * @param fileSize 文件大小
     * @param callBack
     */
    public void uploadSuccess(String bizType, String bizId, String storeKey, String fileName, String fileSize, CallBack callBack, String... strings) {
        AjaxParams params = bankAjaxParams.getUploadSuccess(bizType, bizId, storeKey, fileName, fileSize, strings);
        LogUtils.debug("参数:" + params.getParamsString());
        IBaseMethod.post(context, IBaseUrl.UPLOAD_FILE, params, callBack);
    }

    /**
     * 获取文档收录列表
     *
     * @param callBack
     */
    public void documentList(CallBack callBack) {
        IBaseMethod.post(context, IBaseUrl.BOX_V5_CELL, bankAjaxParams.ajaxDocumentList(), callBack);
    }

    /**
     * 注册一个待入柜文档
     *
     * @param vinNo    vin码
     * @param rfidId   标签编号
     * @param callBack
     */
    public void newDocument(String vinNo, String rfidId, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxNewDocument(vinNo, rfidId);
        IBaseMethod.post(context, IBaseUrl.BOX_V5_CELL, params, callBack);
    }

    /**
     * 监管器绑定-》获取小圆盘列表
     *
     * @param callBack
     */
    public void getBindingLists(CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxBindingLists();
        IBaseMethod.post(context, IBaseUrl.BANK_RF_DEV, params, callBack);
    }

    /**
     * 监管器绑定-》获取小圆盘列表
     *
     * @param callBack
     */
    public void getBindingAdd(String vinNo, String devCode, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxBindingAdd(vinNo, devCode);
        IBaseMethod.post(context, IBaseUrl.BANK_RF_DEV, params, callBack);
    }

    /**
     * 获取经销商列表
     *
     * @param action
     * @param callBack
     */
    public void getDlrList(CallBack callBack, String... action) {
        AjaxParams params = bankAjaxParams.ajaxDlrs(action);
        IBaseMethod.post(context, IBaseUrl.COMPANY, params, callBack);
    }

    /**
     * 盘库-任务列表
     */
    public void getTaskList(CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxTaskList();
        IBaseMethod.post(context, IBaseUrl.CHECK_TASK, params, callBack);
    }

    /**
     * 盘库 --任务所含车辆清单
     */
    public void getTaskItem(String taskCode, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxTaskItem(taskCode);
        IBaseMethod.post(context, IBaseUrl.CHECK_TASK, params, callBack);
    }

    /**
     * 新建一个盘库任务
     */
    public void getAddTask(String userCompanyCode, String checkUser, String checkCompany, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxAddTask(userCompanyCode, checkUser, checkCompany);
        IBaseMethod.post(context, IBaseUrl.CHECK_TASK, params, callBack);
    }


    /**
     * 删除盘库任务
     */
    public void getDelTask(String taskCode, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxDelTask(taskCode);
        IBaseMethod.post(context, IBaseUrl.CHECK_TASK, params, callBack);
    }

    /**
     * 盘库--提交预审
     */
    public void getSubmitTask(String taskCode, String json, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxSubmitTask(taskCode, json);
        IBaseMethod.post(context, IBaseUrl.CHECK_TASK, params, callBack);
    }

    /**
     * 新建车辆-根据VIN码查询车辆品牌
     */
    public void getVinBrand(String vinCode, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxVinBrand(vinCode);
        LogUtils.debug("根据VIN码查询车辆品牌==" + params.getParamsString());
        IBaseMethod.post(context, IBaseUrl.CONF_CAR, params, callBack);
    }

    /**
     * 新建车辆提交
     */
    public void commiNewCar(CallBack callBack, String... strings) {
        AjaxParams params = bankAjaxParams.ajaxCommitNewCar(strings);
        LogUtils.debug("参数:" + params.getParamsString());
        IBaseMethod.post(context, IBaseUrl.URL_CAR, params, callBack);
    }

    /**
     * 车辆列表
     */
    public void newCarList(CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxNewCarList();
        IBaseMethod.post(context, IBaseUrl.URL_CAR, params, callBack);
    }

    /**
     * 查询车辆品牌列表
     * 查询车辆子品牌列表
     * 查询车辆型号列表
     *
     * @param keyword    模糊查询
     * @param brandId    品牌ID
     * @param subBrandId 子品牌ID
     * @param call
     */
    public void httpCat(String keyword, String brandId, String subBrandId, CallBack<Object> call) {
        AjaxParams params = bankAjaxParams.getCat(keyword, brandId, subBrandId);
        IBaseMethod.post(context, IBaseUrl.CONF_CAR, params, call);
    }

    /**
     * 车辆绑标签
     */
    public void httpLable(CallBack callBack, String... strs) {
        AjaxParams params = bankAjaxParams.ajaxAddLable(strs);
        IBaseMethod.post(context, IBaseUrl.URL_RFID, params, callBack);
    }

    /**
     * 监管清单
     */
    public void httpSupervises(CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxSupervises();
        IBaseMethod.post(context, IBaseUrl.URL_CAR, params, callBack);
    }

    /**
     * 监管清单-详情
     */
    public void httpSuperviserDetails(String companyCode, int page, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxSuperviserDetails(companyCode, page);
        IBaseMethod.post(context, IBaseUrl.URL_CAR, params, callBack);
    }

    /**
     * 车辆绑标签列表
     */
    public void lableList(CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxLableList();
        IBaseMethod.post(context, IBaseUrl.URL_CAR, params, callBack);
    }

    /**
     * 我的-统计信息
     */
    public void httpStatistics(CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxStatistics();
        IBaseMethod.post(context, IBaseUrl.URL_CAR, params, callBack);
    }

    /**
     * 申请清单
     */
    public void httpApply(String action, String dlr, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxApply(action, dlr);
        IBaseMethod.post(context, IBaseUrl.URL_LOAN_APP, params, callBack);
    }

    /**
     * 柜子列表
     * */
    public void httpCabinetLists(CallBack callBack){
        AjaxParams params=bankAjaxParams.ajaxCabinetLists();
        IBaseMethod.post(context,IBaseUrl.CABINET_APP_LISTS,params,callBack);
    }
    /**
     * 柜子详情
     * */
    public void httpCabinetDetails(String id,CallBack callBack){
        AjaxParams params=bankAjaxParams.ajaxCabinetDetails(id);
        IBaseMethod.post(context,IBaseUrl.CABINET_APP_LISTS,params,callBack);
    }
    /**
     * 柜子自行监管说明,柜子自行监管开锁,柜子自行监管停止
     * */
    public void httpCabinetLists(String action,String boxCode,String cellId,CallBack callBack){
        AjaxParams params=bankAjaxParams.ajaxCabinetREGMANUAL(boxCode,cellId,action);
        IBaseMethod.post(context,IBaseUrl.ACTION_CABINET_APP_LISTS,params,callBack);
    }
    public void httpCabinetLists(String action,String boxCode,String cellId,String explain,CallBack callBack){
        AjaxParams params=bankAjaxParams.ajaxCabinetREGMANUAL(boxCode,cellId,action,explain);
        IBaseMethod.post(context,IBaseUrl.ACTION_CABINET_APP_LISTS,params,callBack);
    }

    /**
     * 监管器绑定-》获取小圆盘列表
     *
     * @param callBack
     */
    public void getGpsAdd(String vinNo, String devCode, CallBack callBack) {
        AjaxParams params = bankAjaxParams.ajaxGpsAdd(vinNo, devCode);
        IBaseMethod.post(context, IBaseUrl.GPS_URL, params, callBack);
    }

    /**
     * 版本更新提示
     * */
    public void httpAppVersion(CallBack callBack){
        AjaxParams params=bankAjaxParams.ajaxAppVersion();
        IBaseMethod.post(context,"app_version",params,callBack);
    }

    /**
     * 判断VIN码是否存在
     */
    public void httpIsVinExit(final String vinCode, final Handler handler) {
        final FinalDb db = FinalDb.create(context, IBase.BASE_DATE, false);
        //查找数据库中是否存在
        List<VinCodeBean> lists = db.findAllByWhere(VinCodeBean.class, "vinCode=\"" + vinCode + "\" and userid=\"" + IBase.USERID + "\"");
        if (lists.size() > 0) {
            SPUtils.put(context, IBase.USERID + "vinCode", vinCode);
            Message message = new Message();
            message.what = IBase.CONSTANT_TEN;
            message.obj = true;
            handler.sendMessage(message);
            return;
        }
        AjaxParams params = bankAjaxParams.ajaxIsVin("VIN_CHECK", vinCode);
        IBaseMethod.post(context, IBaseUrl.URL_CAR, params, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("验证VIN：" + o.toString());
                Result result = Result.getMcJson(o.toString());
                Message message = new Message();
                message.what = IBase.CONSTANT_TEN;
                message.obj = result.isSuccess();
                handler.sendMessage(message);
                if (!result.isSuccess()) {
                    DialogUtil.jumpCorrectErr(context, "未找到\"" + vinCode + "\"车辆", "继 续", 0, context.getResources().getColor(android.R.color.holo_red_light));
                } else {
                    VinCodeBean vinCodeBean = new VinCodeBean();
                    vinCodeBean.setVinCode(vinCode);
                    vinCodeBean.setUserid(IBase.USERID);
                    vinCodeBean.setSavetime(DateUtil.getSimpleDateYYYYMMDD(System.currentTimeMillis()));
                    db.save(vinCodeBean);
                    SPUtils.put(context, IBase.USERID + "vinCode", vinCode);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                Message message = new Message();
                message.what = IBase.CONSTANT_TEN;
                message.obj = false;
                handler.sendMessage(message);
                if (errorNo == IBase.CONSTANT_ONE) {
                    IBaseMethod.showNetToast(context);
                }
            }
        });
    }

    /**
     * 获取头像
     */
    public void getHeadBg(final ImageView avatarView, final String url, final Handler handler, final int image) {
        new Thread() {
            public void run() {
                URL fileUrl = null;
                try {
                    fileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (bitmap != null)
                                avatarView.setImageBitmap(bitmap);
                            else
                                avatarView.setImageResource(image);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
