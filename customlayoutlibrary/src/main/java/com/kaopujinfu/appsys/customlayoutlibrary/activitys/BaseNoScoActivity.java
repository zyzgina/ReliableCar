package com.kaopujinfu.appsys.customlayoutlibrary.activitys;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
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

/**
 * Created by zuoliji on 2017/3/28.
 */

public abstract class BaseNoScoActivity extends FragmentActivity {
    public Context mContext;
    public LinearLayout rootView;
    public RelativeLayout header;
    public TextView mTvTitle;
    public ImageView top_meun, mtop_back;
    public LoadingDialog dialog;
    public Button top_btn;
    public LinearLayout mBackll;
    public TextView mTopbacktext;
    public ImageView mTopbackicon;
    public boolean isRefresh=true;
    public int page=1;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.RETAIL_ZERO:
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null && dialog.isShowing())
                                dialog.dismiss();
                        }
                    }, 30000);
                    break;
                case IBase.RETAIL_ONE:
                    String nameing = (String) msg.obj;
                    if (dialog != null) {
                        dialog.show();
                        dialog.setLoadingTitle(nameing);
                        handler.sendEmptyMessage(IBase.RETAIL_ZERO);
                    }
                    break;
                case IBase.RETAIL_COUNTDOWN:
                    int time = (int) msg.obj;
                    if (time <= 0)
                        isRefresh = true;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(R.layout.activity_basenosco);
        rootView = (LinearLayout) findViewById(R.id.container_basenosco);
        header = (RelativeLayout) findViewById(R.id.header_basenosco);
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        rootView.addView(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        initBaseView();
        initView();
    }

    private void initBaseView() {
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

    public abstract void initView();

    public void hideHeader() {
        header.setVisibility(View.GONE);
    }
}
