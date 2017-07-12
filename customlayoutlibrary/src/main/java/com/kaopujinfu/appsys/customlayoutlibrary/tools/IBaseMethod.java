package com.kaopujinfu.appsys.customlayoutlibrary.tools;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.BrandBean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils.AjaxParams;
import com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils.OkHttpUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils.StringCallback;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.ToastUtils;

import net.tsz.afinal.FinalDb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

import static com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils.get;

/**
 * 全局方法定义
 * Created by zuoliji on 2017/3/28.
 */

public class IBaseMethod {

    /**
     * 设置状态栏透明
     */
    public static void setBarStyle(Context context, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
            Window window = ((Activity) context).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);//calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int setPaing(Context context) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 提示框弹出
     *
     * @param context
     * @param content 文字信息
     * @param status  状态
     */
    public static void showToast(Context context, String content, int status) {
        switch (status) {
            case IBase.RETAIL_ZERO:
                //失败提示框
                ToastUtils.showFailText(context, content);
                break;
            case IBase.RETAIL_ONE:
                //成功提示框
                ToastUtils.showSuccessText(context, content);
                break;
            case IBase.RETAIL_TWO:
                //信息提示框
                ToastUtils.makeBottomText(context, content).show();
                break;

        }
    }

    /**
     * 判断网络是否连接
     *
     * @param context activity的上下文对象
     */
    public static boolean isNet(Context context) {
        //获取手机所有连接对象
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取NetworkInfo对象
        NetworkInfo info = manager.getActiveNetworkInfo();
        // 判断当前网络状态是否为连接状态
        if (info != null && info.isConnected()) {
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断网络是否为Wifi
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    /**
     * 记录日志
     *
     * @param message 日志信息
     */
    public static void log(String message) {
        String logdir;
        if (Environment.getExternalStorageDirectory() != null) {
            logdir = FileUtils.getCrashLogFilePath() + File.separator + "log";
            File file = new File(logdir);
            boolean mkSuccess;
            if (!file.isDirectory()) {
                mkSuccess = file.mkdirs();
                if (!mkSuccess) {
                    mkSuccess = file.mkdirs();
                }
            }
            try {
                FileWriter fw = new FileWriter(logdir + File.separator + DateUtil.getSimpleDateYYYYMMDDHHMMM(System.currentTimeMillis()) + ".txt", true);
                fw.write(new Date() + "\n");
                fw.write(message);
                fw.close();
            } catch (IOException e) {
                Log.e("crash handler", "load file failed...", e.getCause());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 网络判断
     *
     * @param context
     * @param status  判断是否需要执行网络连接判断
     * @return boolean 是否可以执行下一步
     */
    public static boolean isNetToast(Context context, boolean status) {
        if (status) {
            if (!isNet(context) && !IBase.isOpenNet) {
//                Intent intent = new Intent(context, NetworkActivity.class);
//                context.startActivity(intent);
                return false;
            }
            return true;
        } else {
//            Intent intent = new Intent(context, NetworkActivity.class);
//            context.startActivity(intent);
            return false;
        }
    }

    public static void showNetToast(Context context) {
        DialogUtil.jumpCorrectErr(context, "当前设备未有网络连接,请检查", "继 续", 2, context.getResources().getColor(android.R.color.holo_orange_light));
    }

    private static int time = 60;

    /**
     * 倒计时操作
     *
     * @param timedate 计时 timedate 秒
     * @param handler  timedate 秒后消息处理
     */
    public static void jumpCountdown(int timedate, final Handler handler) {
        time = timedate;
        final Timer timer = new Timer();
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                time--;
                if (time == 0) {
                    //计时完毕，关闭计时器
                    timer.cancel();
                }
                Message msg = new Message();
                msg.what = IBase.RETAIL_COUNTDOWN;
                msg.obj = time;
                handler.sendMessage(msg);
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    /**
     * 调用接口
     *
     * @param url    接口地址
     * @param params 参数
     * @param call   返回接受
     */
    public static void post(final Context context, String url, AjaxParams params, final CallBack call) {
        String urlPath = SPUtils.get(RetailAplication.getContext(), "domain", "").toString();
        //判断是否有网络
        if (!IBaseMethod.isNetToast(context, true)) {
            if (call != null) {
                call.onFailure(IBase.CONSTANT_ONE, "请打开网络");
            }
            return;
        }
        //判断最后是否加了“/”
        int num = urlPath.lastIndexOf("/");
        if (num != urlPath.length() - 1) {
            urlPath += "/";
        }
        //判断是否加了http://
        if (!urlPath.contains("http://")) {
            urlPath = "http://" + urlPath;
        }
        LogUtils.debug("接口地址:"+urlPath);
        urlPath += url;
        OkHttpUtils.asyncPost(urlPath, params, new StringCallback() {
            @Override
            public void onSuccess(Call scall, String result) {
                if (call != null) {
                    call.onSuccess(result);
                }
            }

            @Override
            public void onFailed(Call fCall, IOException e) {
                LogUtils.debug("接口响应:" + e.toString());
//                IBaseMethod.isNetToast(context,false);
                if (call != null) {
                    if (e.toString().contains("host")) {
                        call.onFailure(IBase.CONSTANT_TWO, "请设置IP地址");
                    } else {
                        call.onFailure(500, "接口无响应:" + e.toString());
                    }
                }
            }
        });
    }

    /**
     * 接口响应错误执行的操作
     */
    public static void implementError(Context context, int errorNo) {
        switch (errorNo) {
            case IBase.CONSTANT_ONE:
                //网络错误
//                Intent intent=new Intent(context,NetworkActivity.class);
//                context.startActivity(intent);
                break;
            default:
                //其他错误编码操作
                break;
        }
    }

    /**
     * 隐藏字段
     *
     * @param info  字段
     * @param start 从start开始
     * @param end   隐藏到end
     * @return
     */
    public static String hide(String info, int start, int end) {
        if (info != null && info.length() >= end) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < info.length(); i++) {
                if (i >= start && i <= end) {
                    sb.append("*");
                } else
                    sb.append(info.substring(i, i + 1));
            }
            return sb.toString();
        }
        return info;
    }

    /**
     * 系统出错可能导致登录获取的信息为空
     * 所以当为空的时候便重新获取一遍
     */
    public static void setBaseInfo(Context mContext) {
        if (GeneralUtils.isEmpty(IBase.USERID) || GeneralUtils.isEmpty(IBase.SESSIONID) || GeneralUtils.isEmpty(IBase.COMPANY_CODE)) {
            IBase.USERID = (String) get(mContext, "login_user_id", "");
            IBase.SESSIONID = (String) get(mContext, "login_s_id", "");
            IBase.COMPANY_CODE = (String) get(mContext, "companyCode", "");
        }
    }


    /**
     * 计算距离
     *
     * @param distance
     * @return
     */
    public static String computingDistance(float distance) {
        String kilometre = Math.round(distance / 100d) / 10d + "";
        if (Float.parseFloat(kilometre) <= 1) {
            return distance + "米";
        }
        return kilometre + "公里";
    }

    /**
     * 2  * 获取版本号
     * 3  * @return 当前应用的版本号
     * 4
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return " v " + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "v 1.0";
        }
    }

    /**
     * 保存文件到本地
     *
     * @param db
     * @param strings 需要保存的文件列表
     * @param strs    0,vincode 1,什么类型标签
     */
    public static void saveDateLoaction(FinalDb db, List<String> strings, String... strs) {
        for (String str : strings) {
            File file = new File(str);
            UploadBean uploadBean = saveUploadBean(file, strs);
            List<UploadBean> beanLists = db.findAllByWhere(UploadBean.class, "loactionPath=\"" + str + "\"");
            if (beanLists.size() == 0) {
                LogUtils.debug("保存上传列表：" + uploadBean.toString());
                db.save(uploadBean);
            }
        }

    }

    public static UploadBean saveUploadBean(File file, String... strs) {
        UploadBean uploadBean = new UploadBean();
        uploadBean.setVinCode(strs[0]);
        uploadBean.setLabel(strs[1]);
        uploadBean.setUserid(IBase.USERID);
        String pathName = file.getAbsolutePath().replace(FileUtils.getCarUploadPath(), "");
        String fileName = file.getName();
        if (strs[1].contains("文档")) {
            if (pathName.contains("文档绑定")) {
                fileName = fileName.replace("文档绑定", "documentbinding");
                pathName = pathName.replace("文档绑定", "documentbinding");
            }
            uploadBean.setQny_key("BIND_DOC/" + pathName);
        } else if (strs[1].contains("监管")) {
            uploadBean.setQny_key("BIND_DEV/" + pathName);
        } else if (strs[1].contains("VIN")) {
            uploadBean.setQny_key("VIN_OCR/" + pathName);
        } else if (strs[1].contains("车辆绑标签")) {
            uploadBean.setQny_key("BIND_RFID/" + pathName);
        } else {
            uploadBean.setQny_key("PHOTO/" + chinaChangeEn(pathName));
            fileName = chinaChangeEn(fileName);
        }
        uploadBean.setFilename(fileName);
        uploadBean.setFilesize(file.length() + "");
        uploadBean.setLoactionPath(file.getAbsolutePath());
        if(strs.length>3){
            uploadBean.setLatitude(strs[2]);
            uploadBean.setLongitude(strs[3]);
        }
        return uploadBean;
    }

    /**
     * 照片采集中文转英文
     */
    public static String chinaChangeEn(String paths) {
        if (types != null && typesEn != null) {
            for (int i = 0; i < types.length; i++) {
                if (paths.contains(types[i])) {
                    paths = paths.replace(types[i], typesEn[i]);
                    break;
                }
            }
        }
        if (assessment != null && assessmentEn != null) {
            for (int i = 0; i < assessment.length; i++) {
                if (paths.contains(assessment[i])) {
                    paths = paths.replace(assessment[i], assessmentEn[i]);
                    break;
                }
            }
        }
        LogUtils.debug("转换后的数据:" + paths);
        return paths;
    }

    private static String[] types, typesEn, assessment, assessmentEn;

    public static void getChangeDate(String[] types, String[] typesEn, String[] assessment, String[] assessmentEn) {
        IBaseMethod.types = types;
        IBaseMethod.typesEn = typesEn;
        IBaseMethod.assessment = assessment;
        IBaseMethod.assessmentEn = assessmentEn;
    }

    /**
     * 保存品牌到本地
     *
     * @param lists
     * @param strings
     */
    public static void saveBrand(Context context, FinalDb db, List<BrandBean.BrandItems> lists, String... strings) {
        LogUtils.debug("进入了保存");
        boolean isBrand = (boolean) SPUtils.get(context, "isBrand", false);
        if (!isBrand) {
            SPUtils.put(context, "isBrand", true);
        }
        for (BrandBean.BrandItems itemsEntity : lists) {
            //判断该地址是否在数据库中已经存在
            String quey = "";
            if (GeneralUtils.isEmpty(itemsEntity.getSubBrandId()) && GeneralUtils.isEmpty(itemsEntity.getModelId())) {
                itemsEntity.setLeve(0);
                quey = "brandId=\"" + itemsEntity.getBrandId() + "\"";
            } else if (GeneralUtils.isEmpty(itemsEntity.getModelId())) {
                itemsEntity.setLeve(1);
                quey = "subBrandId=\"" + itemsEntity.getSubBrandId() + "\"";
            } else {
                itemsEntity.setLeve(2);
                itemsEntity.setBrandId(strings[0]);
                itemsEntity.setSubBrandId(strings[1]);
                quey = "modelId=\"" + itemsEntity.getModelId() + "\"";
            }
            if (isBrand) {
                List<BrandBean.BrandItems> list = db.findAllByWhere(BrandBean.BrandItems.class, quey);
                if (list == null || list.size() == 0) {
                    if (!GeneralUtils.isEmpty(itemsEntity.getBrandId()))
                        db.save(itemsEntity);
                }
            } else {
                if (!GeneralUtils.isEmpty(itemsEntity.getBrandId()))
                    db.save(itemsEntity);
            }
        }
//        List<BrandBean.BrandItems> list = db.findAll(BrandBean.BrandItems.class);
//        for (BrandBean.BrandItems itemsEntity : list) {
//            LogUtils.debug("======" + itemsEntity.toString());
//        }
    }

}
