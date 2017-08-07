package com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.SurfaceView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

public class WorkerThread extends Thread {
    private final Context mContext;
    private static final int ACTION_WORKER_THREAD_QUIT = 0X1010; // quit this thread
    private static final int ACTION_WORKER_JOIN_CHANNEL = 0X2010;
    private static final int ACTION_WORKER_LEAVE_CHANNEL = 0X2011;
    private static final int ACTION_WORKER_CONFIG_ENGINE = 0X2012;
    private static final int ACTION_WORKER_PREVIEW = 0X2014;
    public static String appId="";
    public static String recordKey="";

    private static final class WorkerThreadHandler extends Handler {

        private WorkerThread mWorkerThread;

        WorkerThreadHandler(WorkerThread thread) {
            this.mWorkerThread = thread;
        }

        public void release() {
            mWorkerThread = null;
        }

        @Override
        public void handleMessage(Message msg) {
            if (this.mWorkerThread == null) {
                return;
            }
            switch (msg.what) {
                case ACTION_WORKER_THREAD_QUIT:
                    mWorkerThread.exit();
                    break;
                case ACTION_WORKER_JOIN_CHANNEL:
                    String[] data = (String[]) msg.obj;
                    mWorkerThread.joinChannel(data[1], data[0], msg.arg1);
                    break;
                case ACTION_WORKER_LEAVE_CHANNEL:
                    String channel = (String) msg.obj;
                    mWorkerThread.leaveChannel(channel);
                    break;
                case ACTION_WORKER_CONFIG_ENGINE:
                    Object[] configData = (Object[]) msg.obj;
                    mWorkerThread.configEngine((int) configData[0], (String) configData[1], (String) configData[2]);
                    break;
                case ACTION_WORKER_PREVIEW:
                    Object[] previewData = (Object[]) msg.obj;
                    mWorkerThread.preview((boolean) previewData[0], (SurfaceView) previewData[1], (int) previewData[2]);
                    break;
            }
        }
    }

    private WorkerThreadHandler mWorkerHandler;
    private boolean mReady;

    public final void waitForReady() {
        while (!mReady) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        Looper.prepare();
        mWorkerHandler = new WorkerThreadHandler(this);
        ensureRtcEngineReadyLock();
        mReady = true;
        Looper.loop();
    }

    private RtcEngine mRtcEngine;

    public final void enablePreProcessor() {
    }

    public final void disablePreProcessor() {
    }

    /*加入频道*/
    public final void joinChannel(final String channelKey, final String channel, int uid) {
        if (Thread.currentThread() != this) {
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_JOIN_CHANNEL;
            envelop.obj = new String[]{channel, channelKey};
            envelop.arg1 = uid;
            mWorkerHandler.sendMessage(envelop);
            return;
        }
        ensureRtcEngineReadyLock();
        mRtcEngine.joinChannel(channelKey, channel, "OpenVCall", uid);
        mEngineConfig.mChannel = channel;
        mEngineConfig.channelKey = channelKey;
        enablePreProcessor();
    }

    /* 离开频道 */
    public final void leaveChannel(String channel) {
        if (Thread.currentThread() != this) {
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_LEAVE_CHANNEL;
            envelop.obj = channel;
            mWorkerHandler.sendMessage(envelop);
            return;
        }
        if (mRtcEngine != null) {
            mRtcEngine.leaveChannel();
            mRtcEngine.enableVideo();
        }
        disablePreProcessor();
        mEngineConfig.reset();
    }

    private EngineConfig mEngineConfig;

    public final EngineConfig getEngineConfig() {
        return mEngineConfig;
    }

    private final MyEngineEventHandler mEngineEventHandler;

    /* 设置加密、本地视频属性 */
    public final void configEngine(int vProfile, String encryptionKey, String encryptionMode) {
        if (Thread.currentThread() != this) {
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_CONFIG_ENGINE;
            envelop.obj = new Object[]{vProfile, encryptionKey, encryptionMode};
            mWorkerHandler.sendMessage(envelop);
            return;
        }
        ensureRtcEngineReadyLock();
        mEngineConfig.mVideoProfile = vProfile;

        if (!TextUtils.isEmpty(encryptionKey)) {
            //设置内置的加密方案
            mRtcEngine.setEncryptionMode(encryptionMode);
            //启用内置加密功能
            mRtcEngine.setEncryptionSecret(encryptionKey);
        }
        //设置本地视频属性
        mRtcEngine.setVideoProfile(mEngineConfig.mVideoProfile, false);
    }

    /* 开启/停止视频预览 */
    public final void preview(boolean start, SurfaceView view, int uid) {
        if (Thread.currentThread() != this) {
            Message envelop = new Message();
            envelop.what = ACTION_WORKER_PREVIEW;
            envelop.obj = new Object[]{start, view, uid};
            mWorkerHandler.sendMessage(envelop);
            return;
        }
        ensureRtcEngineReadyLock();
        if (start) {
            //设置本地视频显示属性
            mRtcEngine.setupLocalVideo(new VideoCanvas(view, VideoCanvas.RENDER_MODE_HIDDEN, uid));
            mRtcEngine.startPreview();
        } else {
            mRtcEngine.stopPreview();
        }
    }


    /* 初始化RtcEngine对象 */
    private RtcEngine ensureRtcEngineReadyLock() {
        if (mRtcEngine == null) {
            if (TextUtils.isEmpty(appId)) {
                throw new RuntimeException("NEED TO use your App ID, get your own ID at https://dashboard.agora.io/");
            }
            //创建RtcEngine对象
            mRtcEngine = RtcEngine.create(mContext, appId, mEngineEventHandler.mRtcEventHandler);
            //设置频道属性
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
            //打开视频模式
            mRtcEngine.enableVideo();
            //启用说话者音量提示
            mRtcEngine.enableAudioVolumeIndication(200, 3); // 200 ms
            //设置日志文件
            mRtcEngine.setLogFile(FileUtils.getLogFilePath() + "/log/agora-rtc.log");
            //设置服务器录制
            mRtcEngine.startRecordingService(recordKey);
        }
        return mRtcEngine;
    }

    public MyEngineEventHandler eventHandler() {
        return mEngineEventHandler;
    }

    public RtcEngine getRtcEngine() {
        return mRtcEngine;
    }

    /**
     * call this method to exit
     * should ONLY call this method when this thread is running
     */

    public final void exit() {
        if (Thread.currentThread() != this) {
            mWorkerHandler.sendEmptyMessage(ACTION_WORKER_THREAD_QUIT);
            return;
        }

        mReady = false;

        // 待办事项应删除所有挂起（读取）消息。
        // exit thread looper
        Looper.myLooper().quit();
        mWorkerHandler.release();
    }

    public WorkerThread(Context context) {
        this.mContext = context;
        this.mEngineConfig = new EngineConfig();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        this.mEngineConfig.mUid = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_UID, 0);
        this.mEngineEventHandler = new MyEngineEventHandler(mContext, this.mEngineConfig);
    }
}
