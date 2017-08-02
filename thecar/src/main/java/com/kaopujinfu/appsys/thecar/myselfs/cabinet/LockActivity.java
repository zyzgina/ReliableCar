package com.kaopujinfu.appsys.thecar.myselfs.cabinet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.CabinetDetailsBean;

import org.greenrobot.eventbus.EventBus;

/**
 * Describe:对柜子中的格子进行操作
 * Created Author: Gina
 * Created Date: 2017/7/19.
 */

public class LockActivity extends BaseNoScoActivity implements View.OnClickListener {
    private CabinetDetailsBean.CellsEntity cellsEntity;
    private EditText remarksLock;
    private ImageView lockrelatItemLock, superisrelatItemLock, saveelatItemLock;
//    private LinearLayout linearItemLock, linearItemLockSup, linearItemLockSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        cellsEntity = (CabinetDetailsBean.CellsEntity) getIntent().getSerializableExtra("cellsEntity");
        dialog.dismiss();
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText(cellsEntity.getBoxCode());
        top_meun.setVisibility(View.GONE);

        remarksLock = (EditText) findViewById(R.id.remarksLock);
        if (!GeneralUtils.isEmpty(cellsEntity.getCContent()))
            remarksLock.setText(cellsEntity.getCContent());

        lockrelatItemLock = (ImageView) findViewById(R.id.relatItemLock);
        superisrelatItemLock = (ImageView) findViewById(R.id.relatItemLockSup);
        saveelatItemLock = (ImageView) findViewById(R.id.relatItemLockSave);
//        linearItemLock = (LinearLayout) findViewById(R.id.linearItemLock);
//        linearItemLockSup = (LinearLayout) findViewById(linearItemLockSup);
//        linearItemLockSave = (LinearLayout) findViewById(linearItemLockSave);
        initData();

    }

    public void initData() {
//        lockrelatItemLock.getBackground().setAlpha(40);
//        superisrelatItemLock.getBackground().setAlpha(40);
//        saveelatItemLock.getBackground().setAlpha(40);
        lockrelatItemLock.setOnClickListener(this);
        superisrelatItemLock.setOnClickListener(this);
        saveelatItemLock.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == saveelatItemLock) {
            //保存
            setSave();
        } else if (v == superisrelatItemLock) {
            //停止监管
            setSup();
        } else if (v == lockrelatItemLock) {
            //开锁
            setLock();
        }
    }

    /* 开锁 */
    private void setLock() {
        dialog.setLoadingTitle("正在开锁...");
        dialog.show();
        HttpBank.getIntence(this).httpCabinetLists(IBaseUrl.OPEN_MANUAL, cellsEntity.getBoxCode(), cellsEntity.getCId(), new CallBack<Object>() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_ONE) {
                    IBaseMethod.showNetToast(LockActivity.this);
                }
            }

            @Override
            public void onSuccess(Object o) {
                LogUtils.debug(o.toString());
                dialog.dismiss();
                Result result = Result.getMcJson(o.toString());
                if (result != null && result.isSuccess()) {
                    DialogUtil.jumpPrompt(LockActivity.this, "开锁成功", "", 1, getResources().getColor(R.color.check_ok));
                } else {
                    DialogUtil.jumpPrompt(LockActivity.this, "开锁失败", result.getComment(), 0, getResources().getColor(R.color.check_error));
                }
            }
        });

    }

    /* 停止监管 */
    private void setSup() {
        dialog.setLoadingTitle("停止监管操作中...");
        dialog.show();
        HttpBank.getIntence(this).httpCabinetLists(IBaseUrl.STOP_MANUAL, cellsEntity.getBoxCode(), cellsEntity.getCId(), new CallBack<Object>() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_ONE) {
                    IBaseMethod.showNetToast(LockActivity.this);
                }
            }

            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug(o.toString());
                Result result = Result.getMcJson(o.toString());
                if (result != null && result.isSuccess()) {
                    DialogUtil.jumpPrompt(LockActivity.this, "停止监管成功", "", 1, getResources().getColor(R.color.check_ok));
                    JumpEventBus jumpEventBus=new JumpEventBus();
                    jumpEventBus.setName("柜体操作");
                    EventBus.getDefault().post(jumpEventBus);
                } else {
                    DialogUtil.jumpPrompt(LockActivity.this, "停止监管失败", result.getComment(), 0, getResources().getColor(R.color.check_error));
                }
            }
        });
    }

    /* 保存 */
    private void setSave() {
        String explain = remarksLock.getText().toString();
        if (GeneralUtils.isEmpty(explain)) {
            IBaseMethod.showToast(this, "请输入备注说明", IBase.RETAIL_TWO);
            return;
        }
        HttpBank.getIntence(this).httpCabinetLists(IBaseUrl.REG_MANUAL, cellsEntity.getBoxCode(), cellsEntity.getCId(), explain, new CallBack<Object>() {
            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_ONE) {
                    IBaseMethod.showNetToast(LockActivity.this);
                }
            }

            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug(o.toString());
                Result result = Result.getMcJson(o.toString());
                if (result != null && result.isSuccess()) {
                    DialogUtil.jumpPrompt(LockActivity.this, "备注说明成功", "", 1, getResources().getColor(R.color.check_ok));
                    JumpEventBus jumpEventBus=new JumpEventBus();
                    jumpEventBus.setName("柜体操作");
                    EventBus.getDefault().post(jumpEventBus);
                } else {
                    DialogUtil.jumpPrompt(LockActivity.this, "备注说明失败", result.getComment(), 0, getResources().getColor(R.color.check_error));
                }
            }
        });
    }
}
