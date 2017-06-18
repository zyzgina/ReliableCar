package com.kaopujinfu.appsys.customlayoutlibrary.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;

import org.greenrobot.eventbus.EventBus;

/**
 * 无网络界面
 * Created by 左丽姬 on 2017/4/7.
 */

public class NetworkActivity extends Activity {
    private LinearLayout network;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IBase.isOpenNet=true;
        setContentView(R.layout.activity_network);
        network = (LinearLayout) findViewById(R.id.network);
        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发送广播重新加载数据
                JumpEventBus jumpEventBus=new JumpEventBus();
                jumpEventBus.setStatus(IBase.RETAIL_ZERO);
                EventBus.getDefault().post(jumpEventBus);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IBase.isOpenNet=false;
    }
}
