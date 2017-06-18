package com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Callback;

/**
 * 网络请求封装
 * Created by 左丽姬 on 2017/5/11.
 */

public class OkHttpUtils {
    private static SyncOkHttp mSyncOkHttp = new SyncOkHttp();
    private static AsyncOkHttp mAsyncOkHttp = new AsyncOkHttp();
    /**
     * 同步 post 请求
     *  @param url
     *  @param params
     * */
    public static String syncPostString(String url,AjaxParams params)throws IOException {
        return mSyncOkHttp.syncPostString(url,params);
    }

    /**
     * 异步post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调接口
     */
    public static void asyncPost(String url, AjaxParams params, Callback callback) {
        mAsyncOkHttp.asyncPost(url, params, callback);
    }

    /**
     * 上传文件
     *
     * @param url         请求地下
     * @param contentType 需要上传的文件的类型 如:图片"image/jpeg"; "image/png".
     * @param file        要上传的文件
     * @param callback    回调接口
     */
    public static void uploadFile(String url, String contentType, File file, UploadFileCallback callback) {
        mAsyncOkHttp.uploadFile(url, contentType, file, callback);
    }

}
