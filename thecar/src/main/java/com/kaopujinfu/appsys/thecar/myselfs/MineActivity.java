package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.AvatarView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyListView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.ObserveScrollView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.MyselfMissionAdapter;
import com.kaopujinfu.appsys.thecar.adapters.MyselfMsgAadapter;
import com.kaopujinfu.appsys.thecar.adapters.MyselfOperationsAdapter;
import com.kaopujinfu.appsys.thecar.bean.StatisticsBean;
import com.kaopujinfu.appsys.thecar.myselfs.bindings.BindingsActivity;
import com.kaopujinfu.appsys.thecar.myselfs.checks.ChecksActivity;
import com.kaopujinfu.appsys.thecar.myselfs.files.DocumentActivity;
import com.kaopujinfu.appsys.thecar.myselfs.files.MissionCommitActivity;
import com.kaopujinfu.appsys.thecar.myselfs.files.MissionListsActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.CarListActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.LableActivity;
import com.kaopujinfu.appsys.thecar.myselfs.photos.PhotosDetailsActivity;
import com.kaopujinfu.appsys.thecar.upload.UploadListActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Describe: 我的
 * Created Author: Gina
 * Created Date: 2017/6/28.
 */

public class MineActivity extends Activity {
    private RelativeLayout mToplayout;
    private AvatarView mAvatar;
    private TextView mNameTel, mJob;
    private ImageView mTopback;
    private TextView mSpot;
    private MyGridView mMessage, mOperations;
    private MyListView mMission;
    private MyselfMsgAadapter msgAadapter;
    private MyselfMissionAdapter missionAdapter;
    private MyselfOperationsAdapter operationsAdapter;
    private ObserveScrollView myself_scrollview;
    private int cale = 30;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_mine);
        initMyself();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initMyself() {
        mToplayout = (RelativeLayout) findViewById(R.id.top_layout);
        mToplayout.setBackgroundColor(getResources().getColor(R.color.trans));
        mToplayout.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        RelativeLayout myselfTop = (RelativeLayout) findViewById(R.id.myselfTop);
        myselfTop.setPadding(0, IBaseMethod.setPaing(this) + getResources().getDimensionPixelOffset(R.dimen.sp50), 0, 0);
        mTopback = (ImageView) findViewById(R.id.top_back);
        mTopback.setVisibility(View.GONE);
        LinearLayout mBackll;
        mBackll = (LinearLayout) findViewById(R.id.back_ll);
        mBackll.setVisibility(View.VISIBLE);
        ImageView topback_icon = (ImageView) findViewById(R.id.topback_icon);
        topback_icon.setImageResource(R.drawable.home_icon_user);
        mBackll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //退出返回到工具箱
                JumpEventBus eventBus = new JumpEventBus();
                eventBus.setStatus(IBase.CONSTANT_THREE);
                EventBus.getDefault().post(eventBus);
            }
        });
        TextView mTopBackText = (TextView) findViewById(R.id.top_back_text);
        mTopBackText.setText("注销");
        mTopBackText.setVisibility(View.GONE);
        TextView mTitle = (TextView) findViewById(R.id.top_text);
        mTitle.setText("靠谱看车");
        ImageView mTopmeun = (ImageView) findViewById(R.id.top_meun);
        mTopmeun.setImageResource(R.drawable.my_icon_upload);
        mTopmeun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MineActivity.this, UploadListActivity.class);
                startActivity(intent);
            }
        });
        mSpot = (TextView) findViewById(R.id.spot_top);

        myself_scrollview = (ObserveScrollView) findViewById(R.id.myself_scrollview);
        myself_scrollview.setScrollListener(scrollListener);


        mAvatar = (AvatarView) findViewById(R.id.avatar_myself);
        mNameTel = (TextView) findViewById(R.id.nametel_myself);
        mJob = (TextView) findViewById(R.id.job_myself);
        String o = SPUtils.get(MineActivity.this, "loginUser", "").toString();
        Loginbean user = Loginbean.getLoginbean(o);
        if (user != null) {
            if (GeneralUtils.isEmpty(user.getMobile())) {
                mNameTel.setText(user.getName() + "(未绑手机号)");
            } else {
                mNameTel.setText(user.getName() + "(" + IBaseMethod.hide(user.getMobile(), 3, 6) + ")");
            }
            mJob.setText(user.getCompanyShortName() + "-" + user.getRole());
        } else {
            mNameTel.setText("未设置(未绑手机号)");
            mJob.setText("未加入-未设置");
        }
        mMessage = (MyGridView) findViewById(R.id.message_myself);
        msgAadapter = new MyselfMsgAadapter(this);
        mMessage.setAdapter(msgAadapter);
        mOperations = (MyGridView) findViewById(R.id.operations_myself);
        operationsAdapter = new MyselfOperationsAdapter(this);
        mOperations.setAdapter(operationsAdapter);
        mMission = (MyListView) findViewById(R.id.mission_myself);
        missionAdapter = new MyselfMissionAdapter(this);
        mMission.setAdapter(missionAdapter);

        mOperations.setOnItemClickListener(mOperationsItemClick);
        mMessage.setOnItemClickListener(mMessageItemClick);

        mMission.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MineActivity.this, MissionCommitActivity.class);
                startActivity(intent);
            }
        });
        TextView mMore = (TextView) findViewById(R.id.missionTvMy);
        mMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MineActivity.this, MissionListsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 常用操作点击事件监听
     */
    private AdapterView.OnItemClickListener mOperationsItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            jumpOPeration(position);
        }
    };
    /**
     * 盘库监管信息
     */
    private AdapterView.OnItemClickListener mMessageItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LogUtils.debug("获取跟目路:" + FileUtils.getCarUploadPath());
        }
    };

    /**
     * 常用操作点击事件动作处理
     */
    private void jumpOPeration(int position) {
        Intent intent = new Intent();
        switch (position) {
            case IBase.CONSTANT_ZERO:
                intent.setClass(MineActivity.this, CarListActivity.class);
                break;
            case IBase.CONSTANT_ONE:
                    /* 文档收录 */
                intent.setClass(MineActivity.this, DocumentActivity.class);
                break;
            case IBase.CONSTANT_TWO:
                    /* 监管器绑定 */
                intent.setClass(MineActivity.this, BindingsActivity.class);
                break;
            case IBase.CONSTANT_THREE:
                    /* 车辆绑标签 */
                intent.setClass(MineActivity.this, LableActivity.class);
                break;
            case IBase.CONSTANT_FOUR:
                    /* 照片采集 */
                intent.setClass(MineActivity.this, PhotosDetailsActivity.class);
                break;
            case IBase.CONSTANT_FIVE:
                    /* 盘库 */
                intent.setClass(MineActivity.this, ChecksActivity.class);
                break;
        }
        startActivity(intent);
    }

    @Subscribe
    public void onEventMainThread(Object jumpEventBus) {
        if (jumpEventBus instanceof StatisticsBean) {
            msgAadapter.setLists((StatisticsBean) jumpEventBus);
        }
    }

    private boolean isChange = false;
    private ObserveScrollView.ScrollListener scrollListener = new ObserveScrollView.ScrollListener() {
        @Override
        public void scrollOritention(int l, final int t, int oldl, final int oldt) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (t / 50 == 1) {
                        mToplayout.setBackgroundColor(getResources().getColor(R.color.trans));
                    } else if (t % 50 == 0 && t < 700) {
                        LogUtils.debug("进入lll");
                        mToplayout.setBackgroundColor(getResources().getColor(R.color.car_theme));
                        int alpha = (int) (t / 2.5);
                        if (alpha > 255) {
                            alpha = 255;
                        }
                        mToplayout.getBackground().setAlpha(alpha);
                    }
//                    //逐暂变实
//                    if (t > oldt) {
//                        if (t > cale && t < 700) {
//                            isChange = true;
//                            cale = t + 50;
//                            mToplayout.setBackgroundColor(getResources().getColor(R.color.car_theme));
//                            int alpha = (int) (t / 2.5);
//                            if (alpha > 255) {
//                                alpha = 255;
//                            }
//                            mToplayout.getBackground().setAlpha(alpha);
//                        }
//                    } else {
//                        if (t < cale && t > 50) {
//                            //逐暂变透明
//                            cale = t - 50;
//                            mToplayout.setBackgroundColor(getResources().getColor(R.color.car_theme));
//                            int alpha = (int) (t / 2.5);
//                            if (alpha > 255) {
//                                alpha = 255;
//                            }
//                            mToplayout.getBackground().setAlpha(alpha);
//                        } else if (t <= 50) {
//                            //小于30透明
//                            if (isChange) {
//                                isChange = false;
//                                cale = 30;
//                                mToplayout.setBackgroundColor(getResources().getColor(R.color.trans));
//                            }
//                        }
//                    }
                }
            });
        }
    };
}
