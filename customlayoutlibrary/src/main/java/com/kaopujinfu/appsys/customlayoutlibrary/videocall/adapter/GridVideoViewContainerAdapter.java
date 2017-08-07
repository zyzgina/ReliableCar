package com.kaopujinfu.appsys.customlayoutlibrary.videocall.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.kaopujinfu.appsys.customlayoutlibrary.videocall.bean.UserStatusData;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle.ConstantApp;
import com.kaopujinfu.appsys.customlayoutlibrary.videocall.utils.VideoViewEventListener;

import java.util.HashMap;

/**
 * 大视频显示适配器
 * */
public class GridVideoViewContainerAdapter extends VideoViewAdapter {

    public GridVideoViewContainerAdapter(Context context, int localUid, HashMap<Integer, SurfaceView> uids, VideoViewEventListener listener) {
        super(context, localUid, uids, listener);
    }
    @Override
    public void customizedInit(HashMap<Integer, SurfaceView> uids, boolean force) {
        VideoViewAdapterUtil.composeDataItem1(mUsers, uids, mLocalUid); // local uid
        if (force || mItemWidth == 0 || mItemHeight == 0) {
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            int count = uids.size();
            int DividerX = 1;
            int DividerY = 1;
            if (count == 2) {
                DividerY = 2;
            } else if (count >= 3) {
                DividerX = 2;
                DividerY = 2;
            }
            mItemWidth = outMetrics.widthPixels / DividerX;
            mItemHeight = outMetrics.heightPixels / DividerY;
        }
    }

    @Override
    public void notifyUiChanged(HashMap<Integer, SurfaceView> uids, int localUid, HashMap<Integer, Integer> status, HashMap<Integer, Integer> volume) {
        setLocalUid(localUid);
        VideoViewAdapterUtil.composeDataItem(mUsers, uids, localUid, status, volume, mVideoInfo);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        int sizeLimit = mUsers.size();
        if (sizeLimit >= ConstantApp.MAX_PEER_COUNT + 1) {
            sizeLimit = ConstantApp.MAX_PEER_COUNT + 1;
        }
        return sizeLimit;
    }

    public UserStatusData getItem(int position) {
        return mUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        UserStatusData user = mUsers.get(position);

        SurfaceView view = user.mView;
        if (view == null) {
            throw new NullPointerException("SurfaceView destroyed for user " + (user.mUid & 0xFFFFFFFFL) + " " + user.mStatus + " " + user.mVolume);
        }
        return (String.valueOf(user.mUid) + System.identityHashCode(view)).hashCode();
    }
}
