package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.DocumentAdapter;
import com.kaopujinfu.appsys.thecar.bean.DocumentListBean;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

/**
 * 文档收录
 * Created by 左丽姬 on 2017/5/12.
 */
public class DocumentActivity extends BaseNoScoActivity implements View.OnClickListener {

    private RelativeLayout newDocumentLayout;
    private ImageView newDocument;
    private TwinklingRefreshLayout refreshLayout_document;
    private ExpandableListView documentList;
    private LinearLayout newDocumentBottomLayout, newDocumentDelete, documentNoData;
    private TextView newDocumentSelectNum;
    private DocumentAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grouplist);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.VISIBLE);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("文档收录");
        top_btn.setText("选择");
        top_btn.setOnClickListener(this);

        newDocumentBottomLayout = (LinearLayout) findViewById(R.id.newTaskBottomLayout);
        newDocumentSelectNum = (TextView) findViewById(R.id.num);
        newDocumentDelete = (LinearLayout) findViewById(R.id.delete);
        newDocumentDelete.setOnClickListener(this);

        newDocumentLayout = (RelativeLayout) findViewById(R.id.newTaskLayout);
        newDocument = (ImageView) findViewById(R.id.newTask);
        newDocument.setOnClickListener(this);

        documentNoData = (LinearLayout) findViewById(R.id.TaskNoData);
        TextView DocumentDaer = (TextView) findViewById(R.id.TaskDaer);
        DocumentDaer.setText("亲爱的监管员~");
        TextView DocumentMsg = (TextView) findViewById(R.id.TaskMsg);
        DocumentMsg.setText("您还没有文档任务哦~");

        documentList = (ExpandableListView) findViewById(R.id.taskList);
        documentList.setGroupIndicator(null); // 隐藏默认指示器
        mAdapter = new DocumentAdapter(this, documentList);
        mAdapter.setUpdateUIListener(new DocumentAdapter.UpdateUIListener() {
            @Override
            public void updateUI(int num) {
                newDocumentSelectNum.setText("已选择" + num + "个任务");
            }
        });
        documentList.setAdapter(mAdapter);

        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayout_document = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout_task);
        refreshLayout_document.setHeaderView(progressLayout);
        refreshLayout_document.startRefresh();
        refreshLayout_document.setEnableLoadmore(false);
        refreshLayout_document.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (isRefresh) {
                    isRefresh = false;
                    initData();
                } else {
                    refreshLayout.finishRefreshing();
                }
            }
        });
    }

    private void initData() {
        HttpBank.getIntence(this).documentList(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug(o.toString());
                refreshLayout_document.finishRefreshing();
                DocumentListBean listBean = DocumentListBean.getDocumentList(o.toString());
                if (listBean.isSuccess()) {
                    if (page == 1) {
                        refreshLayout_document.finishRefreshing();
                        IBaseMethod.jumpCountdown(60 * 2, handler);
                        mAdapter.delete();
                    }
                    mAdapter.setListBean(listBean);
                    if (mAdapter.getGroupCount() <= 0) {
                        documentList.setVisibility(View.GONE);
                        documentNoData.setVisibility(View.VISIBLE);
                    } else {
                        documentNoData.setVisibility(View.GONE);
                        documentList.setVisibility(View.VISIBLE);
                    }
                } else {
                    documentList.setVisibility(View.GONE);
                    documentNoData.setVisibility(View.VISIBLE);
                    LogTxt.getInstance().writeLog("获取文档收录列表错误，" + listBean.getComment());
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                if (mAdapter.getGroupCount() <= 0) {
                    documentList.setVisibility(View.GONE);
                    documentNoData.setVisibility(View.VISIBLE);
                }
                refreshLayout_document.finishRefreshing();
                LogTxt.getInstance().writeLog("获取文档收录列表失败，错误编码：" + errorNo + "，错误信息：" + strMsg);
                IBaseMethod.showToast(DocumentActivity.this, strMsg, IBase.RETAIL_ZERO);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == newDocument) {
            // 新建
            Intent intent = new Intent(DocumentActivity.this, DocumentNewActivity.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.delete) {
            // 删除
            DialogUtil.prompt(this, "你确定删除任务", "取消", "确定", new DialogButtonListener() {
                @Override
                public void ok() {
                    newDocumentLayout.setVisibility(View.VISIBLE);
                    newDocumentBottomLayout.setVisibility(View.GONE);
                    top_btn.setText("选择");
                    mAdapter.delete();
                    mAdapter.updateSelected(false);
                }

                @Override
                public void cancel() {

                }
            });
        } else if (view == top_btn) {
            // 选择
            if (newDocumentLayout.getVisibility() == View.VISIBLE) {
                newDocumentLayout.setVisibility(View.GONE);
                newDocumentBottomLayout.setVisibility(View.VISIBLE);
                top_btn.setText("取消");
                mAdapter.updateSelected(true);
            } else {
                newDocumentLayout.setVisibility(View.VISIBLE);
                newDocumentBottomLayout.setVisibility(View.GONE);
                top_btn.setText("选择");
                mAdapter.updateSelected(false);
            }
        }
    }

}
