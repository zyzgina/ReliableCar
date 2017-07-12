package com.kaopujinfu.appsys.customlayoutlibrary.okHttpUtils;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/5/17.
 */
class BaseHttp {

    private static final String TAG = "BaseHttp";
    public static OkHttpClient mOkHttpClient = new OkHttpClient();

    /**
     * 添加一个拦截器
     *
     * @param interceptor
     */
    public static void addInterceptor(Interceptor interceptor) {
        LogUtils.debug(TAG, "before " + mOkHttpClient);
        mOkHttpClient = mOkHttpClient.newBuilder()
                .addInterceptor(interceptor)
                .build();
        LogUtils.debug(TAG, "before " + mOkHttpClient);
    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequest() {
        mOkHttpClient.dispatcher().cancelAll();
    }

    /**
     * 根据一个map集合，生成一个给post可用的封装过的FormBody
     *
     * @param params 参数集合
     * @return formbody 实体
     */
    public static FormBody getFormBody(AjaxParams params) {
        if (null == params || params.getParams().size() == 0) {
            return new FormBody.Builder().build();
        }
        Map<String, String> paramsMap = params.getParams();

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> m : paramsMap.entrySet()) {
            if (!GeneralUtils.isEmpty(m.getValue()))
                builder.add(m.getKey(), m.getValue());
        }
        return builder.build();
    }

    /**
     * 同步请求
     *
     * @param request
     * @return
     * @throws IOException
     */
    protected Response syncRequest(Request request) throws IOException {
        LogUtils.debug(TAG, request.url().toString());
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * 异步请求
     *
     * @param request
     * @param callback
     */
    protected void asyncRequest(Request request, Callback callback) {
        LogUtils.debug("接口地址："+request.url().toString());
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 异步请求,上传文件用的,写的超时时间为20秒
     *
     * @param request
     * @param callback
     */
    protected void asyncUploadRequest(Request request, Callback callback) {
        OkHttpClient okHttpClient = mOkHttpClient.newBuilder().writeTimeout(20, TimeUnit.SECONDS).build();
        okHttpClient.newCall(request).enqueue(callback);
    }


    /**
     * 根据urk和params拼接完整的url
     *
     * @param url    base url
     * @param params 请求参数
     * @return 返回完整的url
     */
    public String getRealUrl(String url, AjaxParams params) {
        if (null != params && params.getParamsSize() > 0) {
            url = url + params.getParamsString();
        }
        return url;
    }

    /**
     * 根据请求地址，请求参数，请求方法，创建一个请求实体对像
     *
     * @param url    请求地址
     * @param params 请求参数,请见{@link AjaxParams}
     * @param method 请求方法，请见{@lin
     * @return
     */
    public Request createRequest(String url, AjaxParams params, String method) {
        if (Method.GET.equals(method)) {
            url = getRealUrl(url, params);
            return new Request.Builder().get().url(url).build();
        } else if (Method.POST.equals(method)) {
            FormBody formBody = getFormBody(params);
            return new Request.Builder().post(formBody).url(url).build();
        }
        return new Request.Builder().get().build();
    }

    /**
     * 请求方式,目前只支持GET 与POST
     */
    interface Method {
        public static final String GET = "GET";
        public static final String POST = "POST";
    }

}
