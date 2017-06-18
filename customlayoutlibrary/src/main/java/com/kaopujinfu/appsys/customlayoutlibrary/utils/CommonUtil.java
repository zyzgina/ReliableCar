package com.kaopujinfu.appsys.customlayoutlibrary.utils;

import android.content.Context;

/**
 * 工具类
 * Created by zuoliji on 2017/3/30.
 */
public class CommonUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}

