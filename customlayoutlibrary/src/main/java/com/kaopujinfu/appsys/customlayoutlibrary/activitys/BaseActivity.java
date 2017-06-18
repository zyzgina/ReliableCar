package com.kaopujinfu.appsys.customlayoutlibrary.activitys;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.view.ObserveScrollView;

/**
 * Created by zuoliji on 2017/3/28.
 */

public abstract class BaseActivity extends FragmentActivity{
    public Context mContext;
    private LinearLayout rootView;
    public RelativeLayout header;
    public TextView mTvTitle;
    public ImageView top_meun,mtop_back;
    public LoadingDialog dialog;
    public SwipeRefreshLayout refreshLayout;
    public ObserveScrollView scrollview;
    public Button top_btn;
    public String mc;
    public LinearLayout mBackll;
    public TextView mTopbacktext;
    public ImageView mTopbackicon;

    public  Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case IBase.RETAIL_ZERO:
                    // 隐藏对话框
                    dialog.dismiss();
                    break;
                case IBase.RETAIL_ONE:
                    String nameing= (String) msg.obj;
                    if(dialog!=null) {
                        dialog.show();
                        dialog.setLoadingTitle(nameing);
                    }
                    break;
                case IBase.RETAIL_COUNTDOWN:
                    // mc 值保存5分钟
                    mc = null;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext=this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_base);
        rootView = (LinearLayout) findViewById(R.id.container);
        header = (RelativeLayout) findViewById(R.id.header);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        rootView.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initBaseView();
        initView();
        initData();
    }
    private void initBaseView() {
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshlayout_base);
        refreshLayout.setEnabled(false);
        scrollview = (ObserveScrollView) findViewById(R.id.scrollview_base);
        mTvTitle = (TextView) findViewById(R.id.top_text);
        top_meun = (ImageView) findViewById(R.id.top_meun);
        mtop_back = (ImageView) findViewById(R.id.top_back);
        top_btn = (Button) findViewById(R.id.top_btn);
        mtop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBackll = (LinearLayout) findViewById(R.id.back_ll);
        mTopbacktext = (TextView) findViewById(R.id.top_back_text);
        mTopbackicon = (ImageView) findViewById(R.id.topback_icon);
        mBackll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog = new LoadingDialog(this);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        handler.sendEmptyMessage(IBase.RETAIL_ZERO);
    }

    public abstract void initData();

    public abstract void initView();

    public void hideHeader() {
        header.setVisibility(View.GONE);
    }
}
