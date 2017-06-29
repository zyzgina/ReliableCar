package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;

/**
 * http请求成功返回值
 * Created by Doris on 2017/4/11.
 */

public class Result {
    /**
     * result : FAIL
     * success : false
     * comment : NO
     */

    private String result;
    private boolean success;
    private String comment;

    /**
     * mc解析
     */
    public static Result getMcJson(String result) {
        Result resultBean = null;
        try {
            resultBean = new Gson().fromJson(result, new TypeToken<Result>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.debug("登录解析出错");
        }
        return resultBean;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getResult() {
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result='" + result + '\'' +
                ", success=" + success +
                ", comment='" + comment + '\'' +
                '}';
    }
}
