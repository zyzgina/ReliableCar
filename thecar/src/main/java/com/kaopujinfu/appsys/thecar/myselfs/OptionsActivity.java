package com.kaopujinfu.appsys.thecar.myselfs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.MyselfMsgAadapter;
import com.kaopujinfu.appsys.thecar.adapters.MyselfOperationsAdapter;
import com.kaopujinfu.appsys.thecar.bean.StatisticsBean;
import com.kaopujinfu.appsys.thecar.myselfs.bindings.BindingsActivity;
import com.kaopujinfu.appsys.thecar.myselfs.checks.ChecksActivity;
import com.kaopujinfu.appsys.thecar.myselfs.files.DocumentActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.CarListActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.LableActivity;
import com.kaopujinfu.appsys.thecar.myselfs.photos.PhotosDetailsActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/6/29.
 */

public class OptionsActivity extends Activity {
    private MyGridView mMessage, mOperations;
    private MyselfMsgAadapter msgAadapter;
    private MyselfOperationsAdapter operationsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_options);
        initOptions();
    }
    private void initOptions(){
        mMessage = (MyGridView) findViewById(R.id.message_myself);
        msgAadapter = new MyselfMsgAadapter(this);
        mMessage.setAdapter(msgAadapter);
        mOperations = (MyGridView) findViewById(R.id.operations_myself);
        operationsAdapter = new MyselfOperationsAdapter(this);
        mOperations.setAdapter(operationsAdapter);
        mOperations.setOnItemClickListener(mOperationsItemClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
     * 常用操作点击事件动作处理
     */
    private void jumpOPeration(int position) {
        Intent intent = new Intent();
        switch (position) {
            case IBase.CONSTANT_ZERO:
                intent.setClass(OptionsActivity.this, CarListActivity.class);
                break;
            case IBase.CONSTANT_ONE:
                    /* 文档收录 */
                intent.setClass(OptionsActivity.this, DocumentActivity.class);
                break;
            case IBase.CONSTANT_TWO:
                    /* 监管器绑定 */
                intent.setClass(OptionsActivity.this, BindingsActivity.class);
                break;
            case IBase.CONSTANT_THREE:
                    /* 车辆绑标签 */
                intent.setClass(OptionsActivity.this, LableActivity.class);
                break;
            case IBase.CONSTANT_FOUR:
                    /* 照片采集 */
                intent.setClass(OptionsActivity.this, PhotosDetailsActivity.class);
                break;
            case IBase.CONSTANT_FIVE:
                    /* 盘库 */
                intent.setClass(OptionsActivity.this, ChecksActivity.class);
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

}
