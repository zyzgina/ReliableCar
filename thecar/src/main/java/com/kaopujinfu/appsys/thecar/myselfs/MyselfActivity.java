package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.AvatarView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyListView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.ObserveScrollView;
import com.kaopujinfu.appsys.thecar.PersonalActivity;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.MyselfMissionAdapter;
import com.kaopujinfu.appsys.thecar.adapters.MyselfMsgAadapter;
import com.kaopujinfu.appsys.thecar.adapters.MyselfOperationsAdapter;
import com.kaopujinfu.appsys.thecar.myselfs.bindings.BindingsActivity;
import com.kaopujinfu.appsys.thecar.myselfs.checks.ChecksActivity;
import com.kaopujinfu.appsys.thecar.myselfs.files.DocumentActivity;
import com.kaopujinfu.appsys.thecar.myselfs.files.MissionCommitActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.CarListActivity;
import com.kaopujinfu.appsys.thecar.myselfs.photos.PhotosActivity;
import com.kaopujinfu.appsys.thecar.upload.UploadListActivity;

import net.tsz.afinal.FinalBitmap;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


/**
 * 我的
 * Created by 左丽姬 on 2017/5/11.
 */
public class MyselfActivity extends Activity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
        EventBus.getDefault().register(this);
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
        LinearLayout mLlmyself = (LinearLayout) findViewById(R.id.ll_myself);
        mToplayout.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mLlmyself.setPadding(0, IBaseMethod.setPaing(this) + getResources().getDimensionPixelOffset(R.dimen.sp50), 0, 0);
        mTopback = (ImageView) findViewById(R.id.top_back);
        mTopback.setVisibility(View.GONE);
        LinearLayout mBackll = (LinearLayout) findViewById(R.id.back_ll);
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
                Intent intent = new Intent(MyselfActivity.this, UploadListActivity.class);
                startActivity(intent);
            }
        });
        mSpot = (TextView) findViewById(R.id.spot_top);

        ObserveScrollView myself_scrollview = (ObserveScrollView) findViewById(R.id.myself_scrollview);
        myself_scrollview.setScrollListener(new ObserveScrollView.ScrollListener() {
            @Override
            public void scrollOritention(int l, int t, int oldl, int oldt) {
                if (t > 680) {
                    IBaseMethod.setBarStyle(MyselfActivity.this, getResources().getColor(R.color.car_theme));
                    mToplayout.setBackgroundColor(getResources().getColor(R.color.car_theme));
                } else {
                    IBaseMethod.setBarStyle(MyselfActivity.this, getResources().getColor(R.color.trans));
                    mToplayout.setBackgroundColor(getResources().getColor(R.color.trans));
                }
            }
        });

        mAvatar = (AvatarView) findViewById(R.id.avatar_myself);
        mNameTel = (TextView) findViewById(R.id.nametel_myself);
        mJob = (TextView) findViewById(R.id.job_myself);
        String o = SPUtils.get(MyselfActivity.this, "loginUser", "").toString();
        Loginbean user = Loginbean.getLoginbean(o);
        if (user != null) {
            if (GeneralUtils.isEmpty(user.getMobile())) {
                mNameTel.setText(user.getName() + "(未绑定)");
            } else {
                mNameTel.setText(user.getName() + "(" + IBaseMethod.hide(user.getMobile(), 3, 6) + ")");
            }
            mJob.setText(user.getCompanyShortName() + "-" + user.getRole());
        } else {
            mNameTel.setText("未设置(未绑定)");
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
                Intent intent = new Intent(MyselfActivity.this, MissionCommitActivity.class);
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

        }
    };

    /**
     * 常用操作点击事件动作处理
     */
    private void jumpOPeration(int position) {
        Intent intent = new Intent();
        switch (position) {
            case IBase.CONSTANT_ZERO:
                intent.setClass(MyselfActivity.this, CarListActivity.class);
                break;
            case IBase.CONSTANT_ONE:
                    /* 文档收录 */
                intent.setClass(MyselfActivity.this, DocumentActivity.class);
                break;
            case IBase.CONSTANT_TWO:
                    /* 监管器绑定 */
                intent.setClass(MyselfActivity.this, BindingsActivity.class);
                break;
            case IBase.CONSTANT_THREE:
                    /* 照片采集 */
                intent.setClass(MyselfActivity.this, PhotosActivity.class);
                break;
            case IBase.CONSTANT_FOUR:
                    /* 盘库 */
                intent.setClass(MyselfActivity.this, ChecksActivity.class);
                break;
        }
        startActivity(intent);
    }

    /**
     * 我的任务获取数据
     */
    private void getData() {
    }

    @Subscribe
    public void onEventMainThread(JumpEventBus jumpEventBus) {
        if (jumpEventBus.getStatus() == IBase.CONSTANT_ZERO) {
            if (IBase.user != null) {
                mNameTel.setText(IBaseMethod.hide(IBase.user.getMobile(), 3, 6));
                FinalBitmap.create(MyselfActivity.this).display(mAvatar, "http://192.168.88.129:8081" + IBase.user.getRealHeadImg());
            }
        }
    }
}