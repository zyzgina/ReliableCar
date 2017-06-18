package com.kaopujinfu.appsys.customlayoutlibrary.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.view.RealIconView;

/**
 * Describe: 加载对话框
 * Created Author: Gina
 * Created Date: 2017/6/12.
 */

public class LoadingDialog extends Dialog {
    private Context mContext;
    private RealIconView mLoading;
    private TextView mTextLoagind;
    private CountDownTimerLoad load;

    public LoadingDialog(Context context) {
        super(context);
        this.mContext = context;
        this.setCanceledOnTouchOutside(false);
    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        this.setCanceledOnTouchOutside(false);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        mLoading = (RealIconView) findViewById(R.id.loading_realiconview);
        mLoading.setFlag(true);
        mTextLoagind = (TextView) findViewById(R.id.textLoading);

        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        p.height = (int) (d.getHeight() * 0.25);
        getWindow().setAttributes(p);
    }

    @Override
    public void show() {
        super.show();
        if (load != null)
            load.cancel();
        load = new CountDownTimerLoad(20000, 1000);
        load.start();
        //点击返回键是否取消提示框
        this.setCancelable(false);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
        mLoading.setFlag(false);
    }

    /* 控制加载的时间 */
    class CountDownTimerLoad extends CountDownTimer {

        public CountDownTimerLoad(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            LoadingDialog.this.dismiss();
        }

        @Override
        public void onTick(long millisUntilFinished) {
        }

    }

    public void setLoadingTitle(String title) {
        mTextLoagind.setText(title);
    }

    public void setLoadingTitle(int resId) {
        setLoadingTitle(getContext().getResources().getString(resId));
    }

}
