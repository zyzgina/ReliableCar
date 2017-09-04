package com.kaopujinfu.appsys.thecar;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.VinCodeBean;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.AvatarView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyListView;
import com.kaopujinfu.appsys.thecar.adapters.SimpleListAdapter;
import com.kaopujinfu.appsys.thecar.applys.ApplyActivity;
import com.kaopujinfu.appsys.thecar.bean.StatisticsBean;
import com.kaopujinfu.appsys.thecar.bean.VersionBean;
import com.kaopujinfu.appsys.thecar.loans.LoanFormActivity;
import com.kaopujinfu.appsys.thecar.menu.VerionActivity;
import com.kaopujinfu.appsys.thecar.myselfs.MineActivity;
import com.kaopujinfu.appsys.thecar.supervises.SupervisesActivity;
import com.kaopujinfu.appsys.thecar.upload.UploadListActivity;

import net.tsz.afinal.FinalDb;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

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
        getData();
        deletVin();
        appVersion();
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
        mMyself.setOnClickListener(this);
        mLoanform.setOnClickListener(this);
        mApplyform.setOnClickListener(this);
        mSuperviseform.setOnClickListener(this);
        mRadioMyself.setOnClickListener(this);
        mRadioLoanform.setOnClickListener(this);
        mRadioApplyform.setOnClickListener(this);
        mRadioSuperviseform.setOnClickListener(this);

        View layout = getLocalActivityManager().startActivity("frist", new Intent(CarMainActivity.this, MineActivity.class)).getDecorView();
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
        if (i == R.id.myself_llcar || i == R.id.myself_carmain) {
            setChecks();
//            id_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            //打开手势滑动
            mRadioMyself.setChecked(true);
            View layout = getLocalActivityManager().startActivity("frist", new Intent(CarMainActivity.this, MineActivity.class)).getDecorView();
            mContent.addView(layout);
        } else if (i == R.id.loanform_llcar || i == R.id.loanform_carmain) {
            setChecks();
            mRadioLoanform.setChecked(true);
            View layout = getLocalActivityManager().startActivity("second", new Intent(CarMainActivity.this, LoanFormActivity.class)).getDecorView();
            mContent.addView(layout);
        } else if (i == R.id.applyform_llcar || i == R.id.applyform_carmain) {
            setChecks();
            mRadioApplyform.setChecked(true);
            View layout = getLocalActivityManager().startActivity("third", new Intent(CarMainActivity.this, ApplyActivity.class)).getDecorView();
            mContent.addView(layout);
        } else if (i == R.id.superviseform_llcar || i == R.id.superviseform_carmain) {
            setChecks();
            mRadioSuperviseform.setChecked(true);
            View layout = getLocalActivityManager().startActivity("four", new Intent(CarMainActivity.this, SupervisesActivity.class)).getDecorView();
            mContent.addView(layout);
        }
    }

    private void setChecks() {
//        id_drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        //关闭手势滑动
        mContent.removeAllViews();
        mRadioMyself.setChecked(false);
        mRadioLoanform.setChecked(false);
        mRadioApplyform.setChecked(false);
        mRadioSuperviseform.setChecked(false);
    }

    /*private LinearLayout transparent_ll;*/
    private MyListView list_slidingmenu;
    private AvatarView avatar_slidingmenu;
    private TextView tel_slidingmenu;
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
        ImageView slidingmenuIv = (ImageView) findViewById(R.id.slidingmenuIv);
        String o = SPUtils.get(CarMainActivity.this, "loginUser", "").toString();
        Loginbean user = Loginbean.getLoginbean(o);
        if (user != null) {
            if (GeneralUtils.isEmpty(user.getMobile())) {
                tel_slidingmenu.setText("未绑手机号");
            } else {
                tel_slidingmenu.setText(IBaseMethod.hide(user.getMobile(), 3, 6));
            }
            String urlPath = SPUtils.get(RetailAplication.getContext(), "domain", "").toString();
            //判断是否加了http://
            if (!urlPath.contains("http://")) {
                urlPath = "http://" + urlPath;
            }
            if (!GeneralUtils.isEmpty(user.getHead_img())) {
                //初始化加载中时显示的图片
                HttpBank.getIntence(this).getHeadBg(avatar_slidingmenu, urlPath + user.getHead_img(), handler, R.drawable.avatar_head);
            }
//            if (!GeneralUtils.isEmpty(user.getCompany_logo())) {
            //初始化加载中时显示的图片
//                HttpBank.getIntence(this).getHeadBg(slidingmenuIv,urlPath + user.getCompany_logo(),handler,R.drawable.my_background);
//            }
        } else {
            tel_slidingmenu.setText("未绑手机号");
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
                if (position == 1) {
                    Intent intent = new Intent(CarMainActivity.this, VerionActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("versionEntity",versionEntity);
                    intent.putExtras(bundle);
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
        } else if (jumpEventBus.getStatus() == IBase.RETAIL_THREE) {
            getData();
        }
    }

    /**
     * 获取统计信息
     */
    private void getData() {
        HttpBank.getIntence(this).httpStatistics(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("统计信息:" + o.toString());
                StatisticsBean bean = StatisticsBean.getStatisticsBean(o.toString());
                String flag = "false";
                if (bean != null) {
                    flag = "true";
                    EventBus.getDefault().post(bean);
                }
                JumpEventBus jumpEventBus = new JumpEventBus();
                jumpEventBus.setStatus(IBase.RETAIL_FOUR);
                jumpEventBus.setName(flag);
                EventBus.getDefault().post(jumpEventBus);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                JumpEventBus jumpEventBus = new JumpEventBus();
                jumpEventBus.setStatus(IBase.RETAIL_FOUR);
                jumpEventBus.setName("false");
                EventBus.getDefault().post(jumpEventBus);
            }
        });
    }

    /* 判断vin缓存的数据大小，并处理 */
    private void deletVin() {
        FinalDb db = FinalDb.create(this, IBase.BASE_DATE, false);
        List<VinCodeBean> lists = db.findAll(VinCodeBean.class);
        if (lists.size() >= 500) {
            db.deleteAll(VinCodeBean.class);
        }
    }

    static long mExitTime = 0;

    public static void exit(Context context) {
        if ((System.currentTimeMillis() - mExitTime) > 1000) {
            TextView textView = new TextView(context);
            textView.setText("再按一次退出靠谱看车");
            textView.setTextSize(15);
            textView.setTextColor(context.getResources().getColor(R.color.white));
            textView.setBackgroundResource(R.drawable.button_circular5_car_theme);
            textView.setPadding(40, 30, 40, 30);
            Toast toast = new Toast(context);
            toast.setView(textView);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            mExitTime = System.currentTimeMillis();
        } else {
            System.exit(0);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    VersionBean.VersionEntity versionEntity = null;

    /* 获取 版本更新 */
    private void appVersion() {
        HttpBank.getIntence(this).httpAppVersion(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("版本更新:"+o.toString());
                final VersionBean bean = VersionBean.getVersionBean(o.toString());
                if (bean != null && bean.isSuccess()) {
                    List<VersionBean.VersionEntity> entities = bean.getItems();
                    if (entities.size() > 0) {
                        versionEntity = entities.get(entities.size() - 1);
                        PackageManager manager = CarMainActivity.this.getPackageManager();
                        PackageInfo info = null;
                        try {
                            info = manager.getPackageInfo(CarMainActivity.this.getPackageName(), 0);
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                        String version = info.versionName;
                        int vernum = version.compareTo(versionEntity.getAppVersion());
                        if (vernum < 0) {
                            String verStr= versionEntity.getChangeLog();
                            DialogUtil.versionDlogin(CarMainActivity.this, verStr, "V"+versionEntity.getAppVersion(), new DialogButtonListener() {
                                @Override
                                public void ok() {
                                    Uri uri = Uri.parse("https://www.25pp.com/android/detail_7621405/");
                                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(it);
                                }

                                @Override
                                public void cancel() {

                                }
                            });
                        }else{
                            isUploadFile();
                        }
                    }else{
                        isUploadFile();
                    }
                }else{
                    isUploadFile();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                isUploadFile();
            }
        });
    }

    public void isUploadFile(){
        //查询是否有上传数据
        List<UploadBean> uploadBeens = FinalDb.create(this, IBase.BASE_DATE, true).findAllByWhere(UploadBean.class, "userid=\"" + IBase.USERID + "\"");
        if (uploadBeens.size() > 0) {
            DialogUtil.prompt(this, "上传列表中有文件等待上传，赶快去看看吧！", "稍后上传", "现在上传", new DialogButtonListener() {
                @Override
                public void ok() {
                    Intent intent = new Intent(CarMainActivity.this, UploadListActivity.class);
                    startActivity(intent);
                }

                @Override
                public void cancel() {

                }
            });
        }
    }
}
