package com.kaopujinfu.appsys.customlayoutlibrary.videocall.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.kaopujinfu.appsys.customlayoutlibrary.videocall.utils.VideoViewEventListener;

import java.util.HashMap;

/**
 * 小视频显示适配器
 */
public class SmallVideoViewAdapter extends VideoViewAdapter {
    private int mExceptedUid;

    public SmallVideoViewAdapter(Context context, int localUid, int exceptedUid, HashMap<Integer, SurfaceView> uids, VideoViewEventListener listener) {
        super(context, localUid, uids, listener);
        mExceptedUid = exceptedUid;
    }

    @Override
    public void customizedInit(HashMap<Integer, SurfaceView> uids, boolean force) {
        VideoViewAdapterUtil.composeDataItem(mUsers, uids, mLocalUid, null, null, mVideoInfo, mExceptedUid);
        if (force || mItemWidth == 0 || mItemHeight == 0) {
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            mItemWidth = outMetrics.widthPixels / 4;
            mItemHeight = outMetrics.heightPixels / 4;
        }
    }

    @Override
    public void notifyUiChanged(HashMap<Integer, SurfaceView> uids, int uidExcepted, HashMap<Integer, Integer> status, HashMap<Integer, Integer> volume) {
        mUsers.clear();
        mExceptedUid = uidExcepted;
        VideoViewAdapterUtil.composeDataItem(mUsers, uids, mLocalUid, status, volume, mVideoInfo, uidExcepted);
        notifyDataSetChanged();
    }

    public int getExceptedUid() {
        return mExceptedUid;
    }
}
