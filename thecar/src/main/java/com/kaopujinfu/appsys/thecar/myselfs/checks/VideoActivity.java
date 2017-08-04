package com.kaopujinfu.appsys.thecar.myselfs.checks;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.VideoBaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.adapter.SmallVideoViewAdapter;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.bean.Constant;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.bean.SmallVideoViewDecoration;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.bean.UserStatusData;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.bean.VideoInfoData;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle.AGEventHandler;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle.ConstantApp;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.utils.RtlLinearLayoutManager;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.utils.VideoViewEventListener;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.views.DnyCrlView;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.views.GridVideoViewContainer;
import com.kaopujinfu.appsys.thecar.R;
import com.reliablel.voiceproject.VoiceUtils;

import java.util.HashMap;

import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;

/**
 * Describe:视频通话
 * Created Author: Gina
 * Created Date: 2017/7/19.
 */

public class VideoActivity extends VideoBaseActivity implements AGEventHandler {
    private GridVideoViewContainer mGridVideoViewContainer;
    private final HashMap<Integer, SurfaceView> mUidsList = new HashMap<>(); // uid = 0 || uid == EngineConfig.mUid
    private volatile boolean mVideoMuted = false;
    private volatile boolean mAudioMuted = false;
    private volatile int mAudioRouting = -1; // Default
    private DnyCrlView videoDnyCrlView;
    private RelativeLayout hideVideo;
    private LinearLayout checkLinearVideo;
    private FrameLayout showVideo;
    private ImageView voiceImageVideo, switchcameraImageVideo, muteImageVideo;
    private VoiceUtils voiceUtils;
    private boolean isCall = false;
    private TextView allSupVideo, alwaySupVideo,hsSupVideo,brandSupVideo,vinSupVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        voiceUtils = new VoiceUtils();
        voiceUtils.initialTts(this);
    }

    @Override
    protected void initUIandEvent() {
        event().addEventHandler(this);
        Intent i = getIntent();
        String channelName = i.getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME);
        String channelKey = i.getStringExtra(ConstantApp.ACTION_KEY_CHANNEL_KEY);
        config().mUid = i.getIntExtra(ConstantApp.ACTION_KEY_CHANNEL_UID, 0);
        final String encryptionKey = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY);
        final String encryptionMode = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE);
        doConfigEngine(encryptionKey, encryptionMode);

        mGridVideoViewContainer = (GridVideoViewContainer) findViewById(R.id.grid_video_view_container);
        mGridVideoViewContainer.setItemEventHandler(new VideoViewEventListener() {
            @Override
            public void onItemDoubleClick(View v, Object item) {
                if (mUidsList.size() < 2) {
                    return;
                }
                UserStatusData user = (UserStatusData) item;
                int uid = (user.mUid == 0) ? config().mUid : user.mUid;
                if (mLayoutType == LAYOUT_TYPE_DEFAULT && mUidsList.size() != 1) {
                    switchToSmallVideoView(uid);
                } else {
                    switchToDefaultVideoView();
                }
            }
        });

        //创建渲染图
        SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
        rtcEngine().setupLocalVideo(new VideoCanvas(surfaceV, VideoCanvas.RENDER_MODE_HIDDEN, 0));
        surfaceV.setZOrderOnTop(false);
        surfaceV.setZOrderMediaOverlay(false);

        mUidsList.put(config().mUid, surfaceV); // get first surface view
        mGridVideoViewContainer.initViewContainer(getApplicationContext(), 0, mUidsList); // first is now full view
        worker().preview(true, surfaceV, 0);
        worker().joinChannel(channelKey, channelName, config().mUid);
        //显示房间号
        TextView textChannelName = (TextView) findViewById(R.id.channel_name);
        textChannelName.setText(channelName);
        textChannelName.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        optional();
        LinearLayout bottomContainer = (LinearLayout) findViewById(R.id.bottom_container);
        FrameLayout.MarginLayoutParams fmp = (FrameLayout.MarginLayoutParams) bottomContainer.getLayoutParams();
        fmp.bottomMargin = virtualKeyHeight() + 16;

        videoDnyCrlView = (DnyCrlView) findViewById(R.id.videoDnyCrlView);
        hideVideo = (RelativeLayout) findViewById(R.id.hideVideo);
        hideVideo.getBackground().setAlpha(240);
        showVideo = (FrameLayout) findViewById(R.id.showVideo);
        checkLinearVideo = (LinearLayout) findViewById(R.id.checkLinearVideo);
        voiceImageVideo = (ImageView) findViewById(R.id.voiceImageVideo);
        switchcameraImageVideo = (ImageView) findViewById(R.id.switchcameraImageVideo);
        muteImageVideo = (ImageView) findViewById(R.id.muteImageVideo);
        voiceImageVideo.setColorFilter(getResources().getColor(R.color.plain_gray), PorterDuff.Mode.MULTIPLY);
        switchcameraImageVideo.setColorFilter(getResources().getColor(R.color.plain_gray), PorterDuff.Mode.MULTIPLY);
        muteImageVideo.setColorFilter(getResources().getColor(R.color.plain_gray), PorterDuff.Mode.MULTIPLY);

        allSupVideo = (TextView) findViewById(R.id.allSupVideo);
        alwaySupVideo = (TextView) findViewById(R.id.alwaySupVideo);
        hsSupVideo = (TextView) findViewById(R.id.hsSupVideo);
        brandSupVideo = (TextView) findViewById(R.id.brandSupVideo);
        vinSupVideo = (TextView) findViewById(R.id.vinSupVideo);

    }

    /* 屏幕设置*/
    private void optional() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
    }

    private int getVideoProfileIndex() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        int profileIndex = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, ConstantApp.DEFAULT_PROFILE_IDX);
        if (profileIndex > ConstantApp.VIDEO_PROFILES.length - 1) {
            profileIndex = ConstantApp.DEFAULT_PROFILE_IDX;
            // save the new value
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(ConstantApp.PrefManager.PREF_PROPERTY_PROFILE_IDX, profileIndex);
            editor.apply();
        }
        return profileIndex;
    }

    /* 设置加密、本地视频属性 */
    private void doConfigEngine(String encryptionKey, String encryptionMode) {
        int vProfile = ConstantApp.VIDEO_PROFILES[getVideoProfileIndex()];
        worker().configEngine(vProfile, encryptionKey, encryptionMode);
    }

    /* 打开扬声器 、切换前置/后置摄像头 点击事件 */
    public void onCustomizedFunctionClicked(View view) {
        if (mVideoMuted) {
            onSwitchSpeakerClicked();
        } else {
            onSwitchCameraClicked();
        }
    }

    /* 切换前置/后置摄像头 */
    private void onSwitchCameraClicked() {
        RtcEngine rtcEngine = rtcEngine();
        rtcEngine.switchCamera();
    }

    /* 打开扬声器 */
    private void onSwitchSpeakerClicked() {
        RtcEngine rtcEngine = rtcEngine();
        rtcEngine.setEnableSpeakerphone(mAudioRouting != 3);
    }

    /* 退出视频通话 */
    @Override
    protected void deInitUIandEvent() {
        doLeaveChannel();
        event().removeEventHandler(this);
        mUidsList.clear();
    }

    /* 离开频道 */
    private void doLeaveChannel() {
        worker().leaveChannel(config().mChannel);
        worker().preview(false, null, 0);
    }

    /* 挂断视频 */
    public void onEndCallClicked(View view) {
        if (isCall) {
            String content = "你已挂断视频";
            voiceUtils.startSpeek(content, new VoiceUtils.SpeekEndListener() {
                @Override
                public void setSpeekEndListener(boolean b) {
                    if (b)
                        finish();
                }
            });
        } else {
            finish();
        }

    }

    /* 视频语音转换 */
    public void onVoiceChatClicked(View view) {
        if (mUidsList.size() == 0) {
            return;
        }
        SurfaceView surfaceV = getLocalView();
        ViewParent parent;
        if (surfaceV == null || (parent = surfaceV.getParent()) == null) {
            return;
        }
        RtcEngine rtcEngine = rtcEngine();
        mVideoMuted = !mVideoMuted;
        if (mVideoMuted) {
            //关闭视频模式
            rtcEngine.disableVideo();
        } else {
            //打开视频模式
            rtcEngine.enableVideo();
        }
        ImageView iv = (ImageView) view;
        iv.setImageResource(mVideoMuted ? R.drawable.btn_video : R.drawable.btn_voice);
        hideLocalView(mVideoMuted);
    }

    /* 获取视频显示控件 */
    private SurfaceView getLocalView() {
        for (HashMap.Entry<Integer, SurfaceView> entry : mUidsList.entrySet()) {
            if (entry.getKey() == 0 || entry.getKey() == config().mUid) {
                return entry.getValue();
            }
        }

        return null;
    }

    private void hideLocalView(boolean hide) {
        int uid = config().mUid;
        doHideTargetView(uid, hide);
    }

    private void doHideTargetView(int targetUid, boolean hide) {
        HashMap<Integer, Integer> status = new HashMap<>();
        status.put(targetUid, hide ? UserStatusData.VIDEO_MUTED : UserStatusData.DEFAULT_STATUS);
        if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
            mGridVideoViewContainer.notifyUiChanged(mUidsList, targetUid, status, null);
        } else if (mLayoutType == LAYOUT_TYPE_SMALL) {
            UserStatusData bigBgUser = mGridVideoViewContainer.getItem(0);
            if (bigBgUser.mUid == targetUid) { // big background is target view
                mGridVideoViewContainer.notifyUiChanged(mUidsList, targetUid, status, null);
            } else { // find target view in small video view list
                mSmallVideoViewAdapter.notifyUiChanged(mUidsList, bigBgUser.mUid, status, null);
            }
        }
    }

    /* 禁音 */
    public void onVoiceMuteClicked(View view) {
        if (mUidsList.size() == 0) {
            return;
        }
        RtcEngine rtcEngine = rtcEngine();
        rtcEngine.muteLocalAudioStream(mAudioMuted = !mAudioMuted);
        ImageView iv = (ImageView) view;
        if (mAudioMuted) {
            iv.setColorFilter(getResources().getColor(R.color.car_theme), PorterDuff.Mode.MULTIPLY);
        } else {
            iv.clearColorFilter();
        }
    }

    /* 接受其他用户进入房间信息 */
    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
//        LogUtils.debug("视频通话---接受他人加入");
        voiceUtils.startSpeek(uid + "连接成功");
        isCall = true;
        doRenderRemoteUi(uid);
    }

    /* 处理他人加入之后的界面布局 */
    private void doRenderRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                if (mUidsList.containsKey(uid)) {
                    return;
                }
                SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
                mUidsList.put(uid, surfaceV);
                boolean useDefaultLayout = mLayoutType == LAYOUT_TYPE_DEFAULT && mUidsList.size() != 2;
                surfaceV.setZOrderOnTop(!useDefaultLayout);
                surfaceV.setZOrderMediaOverlay(!useDefaultLayout);
                rtcEngine().setupRemoteVideo(new VideoCanvas(surfaceV, VideoCanvas.RENDER_MODE_HIDDEN, uid));
                if (useDefaultLayout) {
                    switchToDefaultVideoView();
                } else {
                    int bigBgUid = mSmallVideoViewAdapter == null ? uid : mSmallVideoViewAdapter.getExceptedUid();
                    switchToSmallVideoView(bigBgUid);
                }
            }
        });
    }

    /* 成功加入频道 */
    @Override
    public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
//        LogUtils.debug("视频通话---加入频道成功:" + uid);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                SurfaceView local = mUidsList.remove(0);
                if (local == null) {
                    return;
                }
                mUidsList.put(uid, local);
            }
        });
    }

    /* 用户退出频道 */
    @Override
    public void onUserOffline(int uid, int reason) {
//        LogUtils.debug("视频通话用户关闭视频:" + uid);
        doRemoveRemoteUi(uid);
    }

    @Override
    public void onExtraCallback(final int type, final Object... data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                doHandleExtraCallback(type, data);
            }
        });
    }

    private void doHandleExtraCallback(int type, Object... data) {
        int peerUid;
        boolean muted;
        switch (type) {
            case AGEventHandler.EVENT_TYPE_ON_USER_AUDIO_MUTED:
                peerUid = (Integer) data[0];
                muted = (boolean) data[1];
                if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
                    HashMap<Integer, Integer> status = new HashMap<>();
                    status.put(peerUid, muted ? UserStatusData.AUDIO_MUTED : UserStatusData.DEFAULT_STATUS);
                    mGridVideoViewContainer.notifyUiChanged(mUidsList, config().mUid, status, null);
                }
                break;

            case AGEventHandler.EVENT_TYPE_ON_USER_VIDEO_MUTED:
                peerUid = (Integer) data[0];
                muted = (boolean) data[1];
                doHideTargetView(peerUid, muted);
                break;

            case AGEventHandler.EVENT_TYPE_ON_USER_VIDEO_STATS:
                IRtcEngineEventHandler.RemoteVideoStats stats = (IRtcEngineEventHandler.RemoteVideoStats) data[0];
                if (Constant.SHOW_VIDEO_INFO) {
                    if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
                        mGridVideoViewContainer.addVideoInfo(stats.uid, new VideoInfoData(stats.width, stats.height, stats.delay, stats.receivedFrameRate, stats.receivedBitrate));
                        int uid = config().mUid;
                        int profileIndex = getVideoProfileIndex();
                        String resolution = getResources().getStringArray(R.array.string_array_resolutions)[profileIndex];
                        String fps = getResources().getStringArray(R.array.string_array_frame_rate)[profileIndex];
                        String bitrate = getResources().getStringArray(R.array.string_array_bit_rate)[profileIndex];

                        String[] rwh = resolution.split("x");
                        int width = Integer.valueOf(rwh[0]);
                        int height = Integer.valueOf(rwh[1]);

                        mGridVideoViewContainer.addVideoInfo(uid, new VideoInfoData(width > height ? width : height,
                                width > height ? height : width,
                                0, Integer.valueOf(fps), Integer.valueOf(bitrate)));
                    }
                } else {
                    mGridVideoViewContainer.cleanVideoInfo();
                }

                break;

            case AGEventHandler.EVENT_TYPE_ON_SPEAKER_STATS:
                IRtcEngineEventHandler.AudioVolumeInfo[] infos = (IRtcEngineEventHandler.AudioVolumeInfo[]) data[0];

                if (infos.length == 1 && infos[0].uid == 0) { // local guy, ignore it
                    break;
                }
                if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
                    HashMap<Integer, Integer> volume = new HashMap<>();

                    for (IRtcEngineEventHandler.AudioVolumeInfo each : infos) {
                        peerUid = each.uid;
                        int peerVolume = each.volume;

                        if (peerUid == 0) {
                            continue;
                        }
                        volume.put(peerUid, peerVolume);
                    }
                    mGridVideoViewContainer.notifyUiChanged(mUidsList, config().mUid, null, volume);
                }

                break;
            case AGEventHandler.EVENT_TYPE_ON_APP_ERROR:
                int subType = (int) data[0];
                if (subType == ConstantApp.AppError.NO_NETWORK_CONNECTION) {
                    showLongToast(getString(R.string.msg_no_network_connection));
                }
                break;
            case AGEventHandler.EVENT_TYPE_ON_AUDIO_ROUTE_CHANGED:

                break;

        }
    }

    private void doRemoveRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                Object target = mUidsList.remove(uid);
                if (target == null) {
                    return;
                }
                int bigBgUid = -1;
                if (mSmallVideoViewAdapter != null) {
                    bigBgUid = mSmallVideoViewAdapter.getExceptedUid();
                }
                if (mLayoutType == LAYOUT_TYPE_DEFAULT || uid == bigBgUid) {
                    switchToDefaultVideoView();
                } else {
                    switchToSmallVideoView(bigBgUid);
                }
            }
        });
    }

    private SmallVideoViewAdapter mSmallVideoViewAdapter;

    private void switchToDefaultVideoView() {
        if (mUidsList.size() == 1) {
            IBaseMethod.showToast(this, "对方已挂断视频", IBase.RETAIL_TWO);
            voiceUtils.startSpeek("对方已挂断视频", new VoiceUtils.SpeekEndListener() {
                @Override
                public void setSpeekEndListener(boolean b) {
                    if (b) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        finish();
                    }
                }
            });
            return;
        }
        mGridVideoViewContainer.initViewContainer(getApplicationContext(), config().mUid, mUidsList);
        mLayoutType = LAYOUT_TYPE_DEFAULT;
    }

    private void switchToSmallVideoView(int bigBgUid) {
        HashMap<Integer, SurfaceView> slice = new HashMap<>(1);
        slice.put(bigBgUid, mUidsList.get(bigBgUid));
        mGridVideoViewContainer.initViewContainer(getApplicationContext(), bigBgUid, slice);
        bindToSmallVideoView(bigBgUid);
        mLayoutType = LAYOUT_TYPE_SMALL;
    }

    public int mLayoutType = LAYOUT_TYPE_DEFAULT;
    public static final int LAYOUT_TYPE_DEFAULT = 0;//默认视频
    public static final int LAYOUT_TYPE_SMALL = 1;//小视频

    /* 刷新本地显示视频 */
    private void bindToSmallVideoView(int exceptUid) {
        boolean twoWayVideoCall = mUidsList.size() == 2;
        RecyclerView recycler = (RecyclerView) findViewById(R.id.small_video_view_container);
        boolean create = false;
        if (mSmallVideoViewAdapter == null) {
            create = true;
            mSmallVideoViewAdapter = new SmallVideoViewAdapter(this, config().mUid, exceptUid, mUidsList, new VideoViewEventListener() {
                @Override
                public void onItemDoubleClick(View v, Object item) {
                    switchToDefaultVideoView();
                }
            });
            mSmallVideoViewAdapter.setHasStableIds(true);
        }
        recycler.setHasFixedSize(true);
        if (twoWayVideoCall) {
            //视频连接成功
            showVideo.setVisibility(View.VISIBLE);
            hideVideo.setVisibility(View.GONE);
            voiceImageVideo.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.MULTIPLY);
            switchcameraImageVideo.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.MULTIPLY);
            muteImageVideo.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.MULTIPLY);
            voiceImageVideo.setClickable(true);
            switchcameraImageVideo.setClickable(true);
            muteImageVideo.setClickable(true);
            videoDnyCrlView.setFlag(false);
            checkLinearVideo.setVisibility(View.VISIBLE);
            recycler.setLayoutManager(new RtlLinearLayoutManager(this, RtlLinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }
        recycler.addItemDecoration(new SmallVideoViewDecoration());
        recycler.setAdapter(mSmallVideoViewAdapter);
        recycler.setDrawingCacheEnabled(true);
        recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        if (!create) {
            mSmallVideoViewAdapter.setLocalUid(config().mUid);
            mSmallVideoViewAdapter.notifyUiChanged(mUidsList, exceptUid, null, null);
        }
        recycler.setVisibility(View.VISIBLE);
    }

}
