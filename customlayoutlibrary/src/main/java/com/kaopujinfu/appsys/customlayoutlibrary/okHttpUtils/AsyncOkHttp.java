package com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils;

import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/5/17.
 * <p/>
 * 异步okhttp
 */
class AsyncOkHttp extends BaseHttp {
    /**
     * 异步post请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调接口
     */
    public void asyncPost(String url, AjaxParams params, Callback callback) {
        Request request = createRequest(url, params, Method.POST);
        asyncRequest(request, callback);
    }
}
