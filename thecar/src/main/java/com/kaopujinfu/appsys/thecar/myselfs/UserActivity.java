package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.AvatarView;
import com.kaopujinfu.appsys.thecar.CarMainActivity;
import com.kaopujinfu.appsys.thecar.R;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/6/29.
 */

public class UserActivity extends Activity {
    private AvatarView mAvatar;
    private TextView mNameTel, mJob;
    private TwinklingRefreshLayout refreshLayoutMin;
    public boolean isRefresh = true;

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.RETAIL_COUNTDOWN:
                    int time = (int) msg.obj;
                    if (time == 0)
                        isRefresh = true;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        EventBus.getDefault().register(this);
        initUser();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initUser() {
        ImageView logoImageview = (ImageView) findViewById(R.id.logoImageview);
        RelativeLayout myselfTop = (RelativeLayout) findViewById(R.id.myselfTop);
        myselfTop.setPadding(0, IBaseMethod.setPaing(this) + getResources().getDimensionPixelOffset(R.dimen.sp50), 0, 0);
        mAvatar = (AvatarView) findViewById(R.id.avatar_myself);
        mNameTel = (TextView) findViewById(R.id.nametel_myself);
        mJob = (TextView) findViewById(R.id.job_myself);
        String o = SPUtils.get(UserActivity.this, "loginUser", "").toString();
        final Loginbean user = Loginbean.getLoginbean(o);
        if (user != null) {
            if (GeneralUtils.isEmpty(user.getMobile())) {
                mNameTel.setText(user.getName() + "(未绑手机号)");
            } else {
                mNameTel.setText(user.getName() + "(" + IBaseMethod.hide(user.getMobile(), 3, 6) + ")");
            }
            String name=user.getCompanyShortName();
            if(GeneralUtils.isEmpty(name))
                name="";
            mJob.setText(name + "-" + user.getRole());
             String urlPath = SPUtils.get(RetailAplication.getContext(), "domain", "").toString();
            //判断是否加了http://
            if (!urlPath.contains("http://")) {
                urlPath = "http://" + urlPath;
            }
//            if (!GeneralUtils.isEmpty(user.getHead_img())) {
//                //初始化加载中时显示的图片
//                LogUtils.debug("头像=====" + urlPath + user.getHead_img());
//                HttpBank.getIntence(this).getHeadBg(mAvatar,urlPath + user.getHead_img(),handler,R.drawable.avatar_head);
//            }
            if (!GeneralUtils.isEmpty(user.getCompany_logo())) {
                //初始化加载中时显示的图片
                HttpBank.getIntence(this).getHeadBg(mAvatar,urlPath + user.getCompany_logo(),handler,R.drawable.avatar_head);
            }
        } else {
            mNameTel.setText("未设置(未绑手机号)");
            mJob.setText("未加入-未设置");
        }

        RelativeLayout rlUser = (RelativeLayout) findViewById(R.id.rlUser);
        rlUser.requestLayout();

         /* 下拉刷新 */
        refreshLayoutMin = (TwinklingRefreshLayout) findViewById(R.id.refreshLayoutMin);
        refreshLayoutMin.setEnableLoadmore(false);
        ProgressLayout progressLayout = new ProgressLayout(this);
        progressLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        refreshLayoutMin.setHeaderView(progressLayout);
//        refreshLayoutMin.setEnableOverScroll(true);//是否越界弹回
        refreshLayoutMin.setFloatRefresh(true);//悬浮刷新
        refreshLayoutMin.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                if (isRefresh) {
                    isRefresh = false;
                    JumpEventBus jumpEventBus = new JumpEventBus();
                    jumpEventBus.setStatus(IBase.RETAIL_THREE);
                    EventBus.getDefault().post(jumpEventBus);
                } else
                    refreshLayoutMin.finishRefreshing();
            }
        });
    }

    @Subscribe
    public void onEventMainThread(JumpEventBus jumpEventBus) {
        if (jumpEventBus.getStatus() == IBase.RETAIL_FOUR) {
            refreshLayoutMin.finishRefreshing();
            if ("true".equals(jumpEventBus.getName())) {
                IBaseMethod.jumpCountdown(IBase.TIME_REFERSH, handler);
            }
        }
    }

    @Override
    public void onBackPressed() {
        CarMainActivity.exit(this);
    }
}
