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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;

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
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;

import net.tsz.afinal.FinalDb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
//                ToastUtils.showFailText(context, content);
                DialogUtil.jumpCorrectErr(context, content, "继 续", 0, context.getResources().getColor(android.R.color.holo_red_light));
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
     * 字符串验证验证
     *
     * @param verdata
     * @param format
     */
    public static boolean verification(String verdata, String format) {
        Pattern p = Pattern.compile(format);
        Matcher matcher = p.matcher(verdata);
        if (matcher.matches()) {
            LogUtils.debug("验证成功");
            return true;
        }
        LogUtils.debug("验证失败");
        return false;
    }

    /**
     * 判断两个字符串是否相等
     *
     * @param str1
     * @param str2
     */
    public static boolean isEqual(String str1, String str2) {
        if (str1.equals(str2)) {
            return true;
        }
        return false;
    }

    /**
     * gridview横向布局方法
     *
     * @param context  上下文对象
     * @param gridView 计算的GridView
     * @param size     多少条数据
     * @param len      长度
     */
    public static void horizontal_layout(Context context, MyGridView gridView, int size, int len) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (size * (len + 5) * density);
        int itemWidth = (int) (len * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LinearLayout.LayoutParams.FILL_PARENT);
        gridView.setLayoutParams(params);// 设置GirdView布局参数
        gridView.setColumnWidth(itemWidth);// 列表项宽
        gridView.setHorizontalSpacing(10);// 列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size);//总长度
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
            LogUtils.debug("=======判断当前网络状态是否为连接状态=====" + info.isConnected());
            if (info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
//        NetworkInfo[] info = manager.getAllNetworkInfo();
//        if (info != null && info.length > 0) {
//            for (int i = 0; i < info.length; i++) {
//                // 判断当前网络状态是否为连接状态
//                LogUtils.debug("=======判断当前网络状态是否为连接状态====="+info[i].isAvailable());
//                if (info[i] != null && info[i].isAvailable()) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
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
     * 获取手机唯一标识码
     *
     * @param activity
     */
    public static void getCode(Activity activity) {
        String base = "abcdefghijklmnopqrstuvwxyzQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 20; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String m_szLongID = sb.toString() + System.currentTimeMillis();
        IBase.szImei = getMD5(m_szLongID);
        SPUtils.put(activity, "alyed_code", IBase.szImei);
    }

    /**
     * MD5 加密
     *
     * @param info 需要加密的信息
     * @return 返回加密后的信息
     */
    public static String getMD5(String info) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(info.getBytes(), 0, info.length());
        byte p_md5Data[] = m.digest();
        info = "";
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            if (b <= 0xF)
                info += "0";
            info += Integer.toHexString(b);
        }
        return info.toUpperCase();
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
        LogUtils.debug("urlPath====" + urlPath);
        //判断是否加了http://
        if (!urlPath.contains("http://")) {
            urlPath = "http://" + urlPath;
        }
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
     * 获取随机数，从 start 到 end 包括 start
     *
     * @param start 开始值
     * @param end   结束值
     * @return
     */
    public static int getRandomNumber(int start, int end) {
        Random random = new Random();
        return random.nextInt(end) + start;
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
     * 计算百分比
     *
     * @param num   占用
     * @param total 总数
     * @param scale 精确小数几位
     * @return
     */
    public static double accuracy(double num, double total, int scale) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        // 可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        // 模式 例如四舍五入
        df.setRoundingMode(RoundingMode.HALF_UP);
        return num / total;
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
                LogUtils.debug("保存上传列表："+uploadBean.toString());
                db.save(uploadBean);
            }
        }

    }

    public static UploadBean saveUploadBean(File file, String... strs) {
        UploadBean uploadBean = new UploadBean();
        uploadBean.setVinCode(strs[0]);
        uploadBean.setLabel(strs[1]);
        String pathName = file.getAbsolutePath().replace(FileUtils.getCarUploadPath(), "");
        if (strs[1].contains("文档"))
            uploadBean.setQny_key("BIND_DEV/" + pathName);
        else if (strs[1].contains("监管"))
            uploadBean.setQny_key("BIND_DOC/" + pathName);
        else if (strs[1].contains("VIN"))
            uploadBean.setQny_key("VIN_OCR/" + pathName);
        else
            uploadBean.setQny_key("BIND_DEV/" + pathName);
        uploadBean.setFilename(file.getName());
        uploadBean.setFilesize(file.length() + "");
        uploadBean.setLoactionPath(file.getAbsolutePath());
        return uploadBean;
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
