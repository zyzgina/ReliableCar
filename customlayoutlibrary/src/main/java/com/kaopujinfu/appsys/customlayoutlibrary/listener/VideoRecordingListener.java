package com.kaopujinfu.appsys.customlayoutlibrary.listener;

/**
 * 视频录制事件
 * Created by Doris on 2017/5/18.
 */
public interface VideoRecordingListener {

    /**
     * 录制
     */
    void onRecordClicked();

    /**
     * 保存
     */
    void onAcceptButtonClicked();

    /**
     * 取消
     */
    void onDeclineButtonClicked();

}
