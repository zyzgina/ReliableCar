package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.PhotosAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * 照片采集
 * Created by 左丽姬 on 2017/5/12.
 */
public class PhotosActivity extends BaseNoScoActivity implements View.OnClickListener {

    private TwinklingRefreshLayout refreshLayout_photos;
    private ExpandableListView photosList;
    private PhotosAdapter photosAdapter;
    private boolean flag = false;//判读是否选择
    private RelativeLayout addPhotos;
    private View newPhotosBottomLayout;
    private TextView newPhotosSelectNum;
    private LinearLayout delete;
    private LinearLayout photosNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grouplist);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("照片采集");
        top_meun.setVisibility(View.GONE);
        top_btn.setText("选择");
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(this);

        addPhotos = (RelativeLayout) findViewById(R.id.newTaskLayout);
        newPhotosBottomLayout = findViewById(R.id.newTaskBottomLayout);
        newPhotosSelectNum = (TextView) findViewById(R.id.num);
        delete = (LinearLayout) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        ImageView newDocument = (ImageView) findViewById(R.id.newTask);
        newDocument.setOnClickListener(this);
        TextView txtDocument = (TextView) findViewById(R.id.txtTask);
        txtDocument.setText("添加新任务");

        photosList = (ExpandableListView) findViewById(R.id.taskList);
        photosAdapter = new PhotosAdapter(this, photosList);
        photosAdapter.setUpdatePhotoeUIListener(new PhotosAdapter.UpdatePhotoUIListener() {
            @Override
            public void updateUI(int num) {
                newPhotosSelectNum.setText("已选择" + num + "个任务");
            }
        });
        photosList.setAdapter(photosAdapter);
        photosList.setOnItemClickListener(onItemClickListener);

        photosNoData = (LinearLayout) findViewById(R.id.TaskNoData);
        TextView DocumentDaer= (TextView) findViewById(R.id.TaskDaer);
        DocumentDaer.setText("亲爱的摄影师~");
        TextView DocumentMsg= (TextView) findViewById(R.id.TaskMsg);
        DocumentMsg.setText("您还没有任务信息哦~");

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_photos = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_task);
        refreshLayout_photos.setHeaderView(progressLayout);
        refreshLayout_photos.startRefresh();
        refreshLayout_photos.setEnableLoadmore(false);
        refreshLayout_photos.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                refreshLayout_photos.finishRefreshing();
                refreshLayout_photos.setEnableRefresh(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.delete) {
            DialogUtil.prompt(this, "确认删除?", "取消", "删除", new DialogButtonListener() {
                @Override
                public void ok() {
                    photosAdapter.delete();
                    flag = false;
                    top_btn.setText("选择");
                    addPhotos.setVisibility(View.VISIBLE);
                    newPhotosBottomLayout.setVisibility(View.GONE);
                    photosAdapter.updateSelected(flag);
                }

                @Override
                public void cancel() {

                }
            });
        } else if (v == top_btn){
            if (flag) {
                flag = false;
                top_btn.setText("选择");
                addPhotos.setVisibility(View.VISIBLE);
                newPhotosBottomLayout.setVisibility(View.GONE);
            } else {
                flag = true;
                top_btn.setText("取消");
                addPhotos.setVisibility(View.GONE);
                newPhotosBottomLayout.setVisibility(View.VISIBLE);
            }
            photosAdapter.updateSelected(flag);
        } else if (v.getId() == R.id.newTask) {
            Intent intent = new Intent(PhotosActivity.this, PhotosDetailsActivity.class);
            startActivity(intent);
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent=new Intent(PhotosActivity.this,PhotosDetailsActivity.class);
            startActivity(intent);
        }
    };
}
