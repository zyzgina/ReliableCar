package com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils;

import okhttp3.Callback;

/**
 * 网络请求封装
 * Created by 左丽姬 on 2017/5/11.
 */

public class OkHttpUtils {
    private static AsyncOkHttp mAsyncOkHttp = new AsyncOkHttp();

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

}
