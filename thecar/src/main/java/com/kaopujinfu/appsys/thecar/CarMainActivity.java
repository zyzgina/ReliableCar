package com.kaopujinfu.appsys.thecar;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.AvatarView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyListView;
import com.kaopujinfu.appsys.thecar.adapters.SimpleListAdapter;
import com.kaopujinfu.appsys.thecar.applys.ApplyActivity;
import com.kaopujinfu.appsys.thecar.loans.LoanFormActivity;
import com.kaopujinfu.appsys.thecar.myselfs.MyselfActivity;
import com.kaopujinfu.appsys.thecar.supervises.SupervisesActivity;

import net.tsz.afinal.FinalBitmap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by 左丽姬 on 2017/5/12.
 */
public class CarMainActivity extends ActivityGroup implements View.OnClickListener {
    private LoadingDialog dialog;
    private RelativeLayout mTbhost;
    private LinearLayout mContent;
    private LinearLayout mMyself, mLoanform, mApplyform, mSuperviseform;
    private RadioButton mRadioMyself, mRadioLoanform, mRadioApplyform, mRadioSuperviseform;
    private DrawerLayout id_drawerlayout;
    private RelativeLayout slidmenu_rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carmain);
        IBaseMethod.setBarStyle(this, Color.TRANSPARENT);
        EventBus.getDefault().register(this);
        initCarMain();
        setMenu();
    }

    private void initCarMain() {
        dialog = new LoadingDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        mTbhost = (RelativeLayout) findViewById(R.id.tbhostcar);
        mContent = (LinearLayout) findViewById(R.id.contentcar_main);

        mMyself = (LinearLayout) findViewById(R.id.myself_llcar);
        mLoanform = (LinearLayout) findViewById(R.id.loanform_llcar);
        mApplyform = (LinearLayout) findViewById(R.id.applyform_llcar);
        mSuperviseform = (LinearLayout) findViewById(R.id.superviseform_llcar);
        mRadioMyself = (RadioButton) findViewById(R.id.myself_carmain);
        mRadioLoanform = (RadioButton) findViewById(R.id.loanform_carmain);
        mRadioApplyform = (RadioButton) findViewById(R.id.applyform_carmain);
        mRadioSuperviseform = (RadioButton) findViewById(R.id.superviseform_carmain);
        mApplyform.setOnClickListener(this);
        mSuperviseform.setOnClickListener(this);
        mRadioMyself.setOnClickListener(this);
        mRadioLoanform.setOnClickListener(this);
        mRadioApplyform.setOnClickListener(this);
        mRadioSuperviseform.setOnClickListener(this);

        View layout = getLocalActivityManager().startActivity("frist",
                new Intent(CarMainActivity.this, MyselfActivity.class)).getDecorView();
        mContent.addView(layout);

        id_drawerlayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        slidmenu_rel = (RelativeLayout) findViewById(R.id.slidmenu_rel);
        id_drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取屏幕的宽高
                WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                //设置右面的布局位置根据左面菜单的right作为右面布局的left   左面的right+屏幕的宽度（或者right的宽度这里是相等的）为右面布局的right
                mTbhost.layout(slidmenu_rel.getRight(), 0, slidmenu_rel.getRight() + display.getWidth(), display.getHeight());
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        Intent intent = new Intent();
        if (i == R.id.loanform_llcar || i == R.id.loanform_carmain) {
            intent.setClass(this, LoanFormActivity.class);
        } else if (i == R.id.applyform_llcar || i == R.id.applyform_carmain) {
            intent.setClass(this, ApplyActivity.class);
        } else if (i == R.id.superviseform_llcar || i == R.id.superviseform_carmain) {
            intent.setClass(this, SupervisesActivity.class);
        }
        startActivity(intent);

    }

    /*private LinearLayout transparent_ll;*/
    private MyListView list_slidingmenu;
    private AvatarView avatar_slidingmenu;
    private TextView tel_slidingmenu;
    private TextView jodgrade_slidingmenu, integral_slidingmenu;
    private RadioGroup logout_slidingmenu_layout;
    private RadioButton logout_slidingmenu;
    private SimpleListAdapter listAdapter;

    /**
     * 设置侧滑菜单
     */
    private void setMenu() {
        RelativeLayout rl_slidingmenu = (RelativeLayout) findViewById(R.id.rl_slidingmenu);
        rl_slidingmenu.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        list_slidingmenu = (MyListView) findViewById(R.id.list_slidingmenu);
        avatar_slidingmenu = (AvatarView) findViewById(R.id.avatar_slidingmenu);
        tel_slidingmenu = (TextView) findViewById(R.id.tel_slidingmenu);
        integral_slidingmenu = (TextView) findViewById(R.id.integral_slidingmenu);
        jodgrade_slidingmenu = (TextView) findViewById(R.id.jodgrade_slidingmenu);
        String o = SPUtils.get(CarMainActivity.this, "loginUser", "").toString();
        Loginbean user = Loginbean.getLoginbean(o);
        if (user != null) {
            if (GeneralUtils.isEmpty(user.getMobile())) {
                tel_slidingmenu.setText("未绑定");
            } else {
                tel_slidingmenu.setText(IBaseMethod.hide(user.getMobile(), 3, 6));
            }
        } else {
            tel_slidingmenu.setText("未绑定");
        }
        logout_slidingmenu_layout = (RadioGroup) findViewById(R.id.logout_slidingmenu_layout);
        logout_slidingmenu = (RadioButton) findViewById(R.id.logout_slidingmenu);
        listAdapter = new SimpleListAdapter(this);
        list_slidingmenu.setAdapter(listAdapter);
        list_slidingmenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(CarMainActivity.this, UpdatePasswordActivity.class);
                    startActivity(intent);
                }
            }
        });
        avatar_slidingmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarMainActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });
        logout_slidingmenu_layout.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 注销
                dialog.show();
                dialog.setLoadingTitle("正在注销...");
                HttpBank.getIntence(CarMainActivity.this).getAction(IBaseUrl.ACTION_LOGOUT, IBaseUrl.USER, new CallBack<Object>() {
                    @Override
                    public void onSuccess(Object o) {
                        dialog.dismiss();
                        LogUtils.debug("注销登录:" + o.toString());
                        Result result = Result.getMcJson(o.toString());
                        // 注销成功
                        SPUtils.remove(CarMainActivity.this, "login_user_password");
                        Intent intent = new Intent(CarMainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        dialog.dismiss();
                        SPUtils.remove(CarMainActivity.this, "login_user_password");
                        Intent intent = new Intent(CarMainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(JumpEventBus jumpEventBus) {
        if (jumpEventBus.getStatus() == IBase.CONSTANT_THREE) {
            if (id_drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                id_drawerlayout.closeDrawer(Gravity.LEFT);
            } else {
                id_drawerlayout.openDrawer(Gravity.LEFT);
            }
        }
    }
}