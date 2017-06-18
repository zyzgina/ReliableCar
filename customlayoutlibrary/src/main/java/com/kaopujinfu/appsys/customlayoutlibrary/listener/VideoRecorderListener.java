package com.kaopujinfu.appsys.customlayoutlibrary.listener;

/**
 * Created by Doris on 2017/5/18.
 */

public interface VideoRecorderListener {

    void onRecordingStopped(String message);

    void onRecordingStarted();

    void onRecordingSuccess();

    void onRecordingFailed(String message);
}
