package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;

import net.tsz.afinal.FinalBitmap;

/**
 * Created by Doris on 2017/5/11.
 */

public class VideoPlayer extends RelativeLayout implements View.OnClickListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    private RelativeLayout videoLayout; // 视频布局
    private CustomVideoView player; // 视频播放器
    private ImageView thumbVideo, videoPlay, videoFull; // 视频封面、 播放、全屏
    private ProgressBar videoloading; // 视频加载控件
    private LinearLayout videoControlLayout; // 视频播放控制器布局
    private TextView videoCurrentTime, videoTotalTime; // 当前播放时间、视频总时长
    private SeekBar videoSeek; // 当前播放进度

    private Activity mActivity;
    private boolean isFullScreen = false; // 是否是全屏

    private static final int UPDATE_UI = 0;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_UI:
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 5秒钟隐藏视频控制器布局
                            videoControlLayout.setVisibility(View.GONE);
                        }
                    }, 5000);
                    break;
            }
        }
    };

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.layout_videoview, this);

        videoLayout = (RelativeLayout) findViewById(R.id.videoLayout_custom);
        player = (CustomVideoView) findViewById(R.id.player_custom);
        thumbVideo = (ImageView) findViewById(R.id.thumbVideo_custom);
        videoPlay = (ImageView) findViewById(R.id.videoPlay_custom);
        videoFull = (ImageView) findViewById(R.id.videoFull_custom);
        videoloading = (ProgressBar) findViewById(R.id.videoloading_custom);
        videoControlLayout = (LinearLayout) findViewById(R.id.videoControlLayout_custom);
        videoCurrentTime = (TextView) findViewById(R.id.videoCurrentTime_custom);
        videoTotalTime = (TextView) findViewById(R.id.videoTotalTime_custom);
        videoSeek = (SeekBar) findViewById(R.id.videoSeek_custom);

        mActivity = (Activity) getContext();

        setPlayingEvent();
    }

    private void setPlayingEvent(){
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        videoSeek.setOnSeekBarChangeListener(this);
        videoPlay.setOnClickListener(this);
        videoFull.setOnClickListener(this);
        videoLayout.setOnClickListener(this);
    }

    private void setVideoViewScale(int width, int heigth) {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = heigth;
        setLayoutParams(layoutParams);
    }

    private void hideOrShowView(int flag, View...views){
        if (views != null && views.length > 0){
            for (View view : views){
                view.setVisibility(flag);
            }
        }
    }

    public void setVideo(String videoUrl, String imageUrl){
        player.setVideoURI(Uri.parse(videoUrl));
        FinalBitmap.create(getContext()).display(thumbVideo, imageUrl);
    }

    public boolean onBack() {
        if (isFullScreen) {
            // 横屏状态切为竖屏
            mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        return !isFullScreen;
    }

    public void setConfigurationChanged(View...views){
        // 监听屏幕方向改变
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 当屏幕方向为横屏的时候
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            isFullScreen = true;
            videoFull.setVisibility(View.GONE);
            hideOrShowView(GONE, views);
            // 移除半屏状态
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            // 设置全屏状态
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            // 当屏幕方向为竖屏的时候
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelOffset(R.dimen.common_measure_480sp));
            isFullScreen = false;
            videoFull.setVisibility(View.VISIBLE);
            hideOrShowView(VISIBLE, views);
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.videoFull_custom){
            if (isFullScreen) {
                // 横屏状态切为竖屏
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else {
                // 竖屏状态切为横屏
                mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else if (view.getId() == R.id.videoLayout_custom){
            mHandler.removeMessages(UPDATE_UI);
            if (videoControlLayout.getVisibility() == View.GONE){
                videoControlLayout.setVisibility(View.VISIBLE);
                if (player.isPlaying()){
                    mHandler.sendEmptyMessage(UPDATE_UI);
                }
            }
        } else if (view.getId() == R.id.videoPlay_custom){
            if (player.isPlaying()) {
                // 正在播放：暂停
                videoPlay.setImageResource(R.drawable.small_play);
                player.pause();
                mHandler.removeMessages(UPDATE_UI);
                videoControlLayout.setVisibility(View.VISIBLE);
            } else {
                // 暂停状态：播放
                videoPlay.setImageResource(R.drawable.small_pause);
                player.start();
                mHandler.sendEmptyMessage(UPDATE_UI);
                if (thumbVideo.getVisibility() == View.VISIBLE) {
                    thumbVideo.setVisibility(View.GONE);
                }
                if (videoloading.getVisibility() == View.INVISIBLE) {
                    videoloading.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void updateTextViewWithTimeFormat(TextView textView, int millisecond) {
        int second = millisecond / 1000;
        int hh = second / 3600; // 小时
        int mm = second % 3600 / 60; // 分钟
        int ss = second % 60; // 秒
        String str = null;
        if (hh != 0) {
            str = String.format("%02d:%02d:%02d", hh, mm, ss);
        } else {
            str = String.format("%02d:%02d", mm, ss);
        }
        textView.setText(str);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        // 视频加载完毕
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
                if (videoloading.getVisibility() == View.VISIBLE) {
                    videoloading.setVisibility(View.GONE);
                }
                // 刷新视频播放时间
                int currentPosition = player.getCurrentPosition();
                int duration = player.getDuration();
                updateTextViewWithTimeFormat(videoCurrentTime, currentPosition);
                updateTextViewWithTimeFormat(videoTotalTime, duration);
                videoSeek.setMax(duration);
                videoSeek.setProgress(currentPosition);
                videoSeek.setSecondaryProgress(i);
            }
        });
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        // 播放结束
        mHandler.removeMessages(UPDATE_UI); // 移除隐藏控制栏事件
        videoControlLayout.setVisibility(View.VISIBLE); // 显示控制栏
        videoPlay.setImageResource(R.drawable.small_play); // 设置为播放按钮
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // 拖动进度条使视频从指定时间点播放
        int progress = seekBar.getProgress();
        videoloading.setVisibility(View.VISIBLE);
        player.seekTo(progress);
    }

}
