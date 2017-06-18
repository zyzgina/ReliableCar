package com.kaopujinfu.appsys.customlayoutlibrary.listener;

/**
 * 对话框确定取消按钮点击事件
 * Created by Doris on 2017/4/24.
 */

public interface DialogReleaseListener {

    /**
     * 确定
     */
    public void okClick(int position);

    /**
     * 取消
     */
    public void cancel();
}
