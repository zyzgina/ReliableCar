package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.AvatarView;
import com.kaopujinfu.appsys.thecar.R;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/6/29.
 */

public class UserActivity extends Activity {
    private AvatarView mAvatar;
    private TextView mNameTel, mJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initUser();
    }
    private void initUser(){
        RelativeLayout myselfTop = (RelativeLayout) findViewById(R.id.myselfTop);
        myselfTop.setPadding(0, IBaseMethod.setPaing(this) + getResources().getDimensionPixelOffset(R.dimen.sp50), 0, 0);
        mAvatar = (AvatarView) findViewById(R.id.avatar_myself);
        mNameTel = (TextView) findViewById(R.id.nametel_myself);
        mJob = (TextView) findViewById(R.id.job_myself);
        String o = SPUtils.get(UserActivity.this, "loginUser", "").toString();
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
        ImageView logoImageview= (ImageView) findViewById(R.id.logoImageview);
    }
}
