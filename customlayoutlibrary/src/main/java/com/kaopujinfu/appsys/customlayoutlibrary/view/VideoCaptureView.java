package com.kaopujinfu.appsys.customlayoutlibrary.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.VideoRecordingListener;

/**
 * Created by Doris on 2017/5/18.
 */

public class VideoCaptureView extends FrameLayout implements View.OnClickListener{

    private SurfaceView videoCaptureView;
    private TextView videoCaptureTimer, videoCaptureHint;
    private ImageView videoCaptureRecord, videoCaptureBack, videoCaptureConfirm, videoCapturePic;
   private LinearLayout videoCaptureBottomLayout;

    private VideoRecordingListener mListener;
    private Handler customHandler = new Handler();
    private long startTime = 0L;
    private boolean mShowTimer;

    public VideoCaptureView(Context context) {
        super(context);
        initialize(context);
    }

    public VideoCaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public VideoCaptureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        final View view = View.inflate(context, R.layout.layout_videocaptureview, this);

        videoCaptureRecord = (ImageView) view.findViewById(R.id.videoCaptureRecord);
        videoCaptureBack = (ImageView) view.findViewById(R.id.videoCaptureBack);
        videoCaptureConfirm = (ImageView) view.findViewById(R.id.videoCaptureConfirm);
        videoCapturePic = (ImageView) view.findViewById(R.id.videoCapturePic);
        videoCaptureBottomLayout = (LinearLayout) view.findViewById(R.id.videoCaptureBottomLayout);
        videoCaptureView = (SurfaceView) view.findViewById(R.id.videoCaptureView);
        videoCaptureTimer = (TextView) view.findViewById(R.id.videoCaptureTimer);
        videoCaptureHint = (TextView) view.findViewById(R.id.videoCaptureHint);

        videoCaptureRecord.setOnClickListener(this);
        videoCaptureConfirm.setOnClickListener(this);
        videoCaptureBack.setOnClickListener(this);
    }

    public void setVideoRecordingListener(VideoRecordingListener listener){
        this.mListener = listener;
    }

    public SurfaceHolder getPreviewSurfaceHolder() {
        return videoCaptureView.getHolder();
    }

    public void updateUINotRecording() {
        videoCaptureRecord.setSelected(false);
        videoCaptureRecord.setImageResource(R.drawable.recording_btn_normal);
        videoCaptureRecord.setVisibility(View.VISIBLE);
        videoCaptureHint.setVisibility(View.VISIBLE);
        videoCaptureHint.setText("点击开始录制");
        videoCaptureBottomLayout.setVisibility(View.GONE);
        videoCapturePic.setVisibility(View.GONE);
        videoCaptureView.setVisibility(View.VISIBLE);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            updateRecordingTime(seconds, minutes);
            customHandler.postDelayed(this, 1000);
        }
    };

    private void updateRecordingTime(int seconds, int minutes) {
        videoCaptureTimer.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }

    public void updateUIRecordingOngoing() {
        videoCaptureRecord.setSelected(true);
        videoCaptureRecord.setImageResource(R.drawable.recording_btn_press);
        videoCaptureRecord.setVisibility(View.VISIBLE);
        videoCaptureHint.setText("点击结束录制");
        videoCaptureHint.setVisibility(View.VISIBLE);
        videoCaptureBottomLayout.setVisibility(View.GONE);
        videoCapturePic.setVisibility(View.GONE);
        videoCaptureView.setVisibility(View.VISIBLE);
        if (mShowTimer) {
            videoCaptureTimer.setVisibility(View.VISIBLE);
            startTime = SystemClock.uptimeMillis();
            updateRecordingTime(0, 0);
            customHandler.postDelayed(updateTimerThread, 1000);
        }
    }

    public void updateUIRecordingFinished(Bitmap videoThumbnail) {
        videoCaptureRecord.setVisibility(View.GONE);
        videoCaptureHint.setVisibility(View.GONE);
        videoCaptureView.setVisibility(View.GONE);
        videoCaptureBottomLayout.setVisibility(View.VISIBLE);
        videoCapturePic.setVisibility(View.VISIBLE);
        if (videoThumbnail != null) {
            videoCapturePic.setScaleType(ImageView.ScaleType.CENTER_CROP);
            videoCapturePic.setImageBitmap(videoThumbnail);
        }
        customHandler.removeCallbacks(updateTimerThread);
    }

    public void setShowTimer(boolean mShowTimer) {
        this.mShowTimer = mShowTimer;
    }

    @Override
    public void onClick(View view) {
        if (mListener == null){
            // 没有设置事件
            return;
        }

        if (view.getId() == R.id.videoCaptureRecord) {
            // 录制视频
            mListener.onRecordClicked();
        } else if (view.getId() == R.id.videoCaptureBack) {
            // 取消保存
            mListener.onDeclineButtonClicked();
        } else if (view.getId() == R.id.videoCaptureConfirm) {
            // 保存
            mListener.onAcceptButtonClicked();
        }
    }
    public void setVisGone(int gones){
        videoCaptureBack.setVisibility(gones);
    }
}
