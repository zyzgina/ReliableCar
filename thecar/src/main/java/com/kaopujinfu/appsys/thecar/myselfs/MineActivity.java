package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.view.ObserveScrollView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.upload.UploadListActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * Describe: 我的
 * Created Author: Gina
 * Created Date: 2017/6/28.
 */

public class MineActivity extends ActivityGroup implements View.OnClickListener {
    private RelativeLayout mToplayout;
    private ImageView mTopback;
    private TextView mSpot;
    private ObserveScrollView myself_scrollview;
    private LinearLayout mineLiner;
    private View layout, layout1, layout2, layout3;
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
        setContentView(R.layout.activity_mine);
        initMyself();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initMyself() {
        mToplayout = (RelativeLayout) findViewById(R.id.top_layout);
        mToplayout.setBackgroundColor(getResources().getColor(R.color.trans));
        mToplayout.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
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
        mineLiner = (LinearLayout) findViewById(R.id.mineLiner);
        mineLiner.setOnClickListener(this);
        layout = getLocalActivityManager().startActivity("zero", new Intent(MineActivity.this, UserActivity.class)).getDecorView();
        layout1 = getLocalActivityManager().startActivity("one", new Intent(MineActivity.this, StatisActivity.class)).getDecorView();
        layout2 = getLocalActivityManager().startActivity("two", new Intent(MineActivity.this, OptionsActivity.class)).getDecorView();
//        layout3 = getLocalActivityManager().startActivity("three", new Intent(MineActivity.this, MissionActiviy.class)).getDecorView();
        mineLiner.addView(layout);
        mineLiner.addView(layout1);
        mineLiner.addView(layout2);
//        mineLiner.addView(layout3);
        layout.setOnClickListener(this);
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
    }

    private ObserveScrollView.ScrollListener scrollListener = new ObserveScrollView.ScrollListener() {
        @Override
        public void scrollOritention(int l, final int t, int oldl, final int oldt) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //设置顶部栏逐渐透明
                    setAlpha(t, oldt);
                    //控制top的显示与隐藏
                    setTopShow();
                }
            });
        }
    };

    /* 设置顶部栏逐渐透明 */
    private boolean isChange = false;

    private void setAlpha(int t, int oldt) {
        //逐暂变实
        if (t > oldt) {
            if (t > cale && t < 700) {
                isChange = true;
                cale = t + 50;
                mToplayout.setBackgroundColor(getResources().getColor(R.color.car_theme));
                int alpha = (int) (t / 2.5);
                if (alpha > 255) {
                    alpha = 255;
                }
                mToplayout.getBackground().setAlpha(alpha);
            }
        } else {
            if (t < cale && t > 50) {
                //逐暂变透明
                cale = t - 50;
                mToplayout.setBackgroundColor(getResources().getColor(R.color.car_theme));
                int alpha = (int) (t / 2.5);
                if (alpha > 255) {
                    alpha = 255;
                }
                mToplayout.getBackground().setAlpha(alpha);
            } else if (t <= 50) {
                //小于30透明
                if (isChange) {
                    isChange = false;
                    cale = 30;
                    mToplayout.setBackgroundColor(getResources().getColor(R.color.trans));
                }
            }
        }
    }

    /* 控制top的显示与隐藏 */
    boolean flag = false, isShow = true, isTopShow = false;

    private void setTopShow() {
        int scorllY = myself_scrollview.getScrollY();
        View view = mineLiner.getChildAt(0);
        int heigthY = view.getHeight() - mToplayout.getHeight();
        if (!flag && scorllY > 80 && scorllY <= heigthY) {
            flag = true;
            isTopShow = true;
            isShow = true;
            mToplayout.setVisibility(View.GONE);
            Animation mSet = AnimationUtils.loadAnimation(MineActivity.this, R.anim.top_move);
            mToplayout.setAnimation(mSet);
        }
        if (isTopShow && scorllY < 80) {
            isTopShow = false;
            flag = false;
            mToplayout.setVisibility(View.VISIBLE);
            Animation mSet = AnimationUtils.loadAnimation(MineActivity.this, R.anim.top_go);
            mToplayout.setAnimation(mSet);
        }
        if (isShow && scorllY > heigthY) {
            isShow = false;
            flag = false;
            mToplayout.setVisibility(View.VISIBLE);
            Animation mSet = AnimationUtils.loadAnimation(MineActivity.this, R.anim.top_go);
            mToplayout.setAnimation(mSet);
        }
    }

    /* 点击显示顶部 */
    private void showTop() {
        int scorllY = myself_scrollview.getScrollY();
        View view = mineLiner.getChildAt(0);
        int heigthY = view.getHeight() - mToplayout.getHeight();
        if (flag && scorllY > 80 && scorllY <= heigthY) {
            flag = false;
            isTopShow = false;
            isShow = false;
            mToplayout.setVisibility(View.VISIBLE);
            Animation mSet = AnimationUtils.loadAnimation(MineActivity.this, R.anim.top_go);
            mToplayout.setAnimation(mSet);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == layout || v == layout1 || v == layout2 || v == mineLiner || v == layout3) {
            showTop();
        }
    }
}
