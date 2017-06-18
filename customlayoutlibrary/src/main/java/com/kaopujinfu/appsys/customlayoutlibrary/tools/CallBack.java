package com.kaopujinfu.appsys.customlayoutlibrary.tools;

/**
 * 接口请求回调函数
 * Created by Doris on 2017/4/11.
 */

public interface CallBack<T>  {

    /**
     * 成功
     * @param t
     */
    public  void onSuccess(T t);

    /**
     * 失败
     * @param errorNo
     * @param strMsg
     */
    public void onFailure(int errorNo, String strMsg);
}
