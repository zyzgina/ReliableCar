package com.github.reliablecar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kaopujinfu.appsys.thecar.LoginActivity;

/**
 * 启动界面
 * author : zuoliji
 * date   :2017/3/28
 */
public class StartActivity extends AppCompatActivity {

    private ImageView image_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initStart();
    }

    /**
     * 初始化页面方法
     */
    private void initStart() {
        image_start = (ImageView) findViewById(R.id.image_start);
        //启动动画
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.start);
        animation.setAnimationListener(animationListener);
        image_start.setAnimation(animation);
    }

    /**
     * 动画监听
     */
    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            //动画开始
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
}
