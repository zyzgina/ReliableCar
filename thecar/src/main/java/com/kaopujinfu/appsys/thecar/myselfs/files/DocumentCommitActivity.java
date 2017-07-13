package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.myselfs.bindings.AddBindingActivity;
import com.kaopujinfu.appsys.thecar.myselfs.bindings.BindingsActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.AddLableActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.CarListActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.LableActivity;
import com.kaopujinfu.appsys.thecar.myselfs.newcar.NewCarActivity;
import com.kaopujinfu.appsys.thecar.myselfs.photos.PhotosDetailsActivity;
import com.kaopujinfu.appsys.thecar.upload.UploadListActivity;

import static com.kaopujinfu.appsys.thecar.R.id.new_documentCommit;


/**
 * 文档提交成功
 * Created by Doris on 2017/5/15.
 */
public class DocumentCommitActivity extends BaseNoScoActivity {
    int success;
    String UploadPath;
    private RelativeLayout commitShot, commitDocument, commitJianguan, commitBiaoqian, commitNewCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RetailAplication.getInstance().addActivity(this);
        setContentView(R.layout.activity_document_commit);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        success = getIntent().getIntExtra("success", 0);
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.GONE);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        Button nextList_documentCommit = (Button) findViewById(R.id.nextList_documentCommit);
        Button new_documentCommit = (Button) findViewById(R.id.new_documentCommit);
        TextView txt_documentCommit = (TextView) findViewById(R.id.txt_documentCommit);
        TextView toast_documentCommit = (TextView) findViewById(R.id.toast_documentCommit);
        TextView sel_documentCommit = (TextView) findViewById(R.id.sel_documentCommit);

        commitNewCar = (RelativeLayout) findViewById(R.id.commitNewCar);
        commitBiaoqian = (RelativeLayout) findViewById(R.id.commitBiaoqian);
        commitJianguan = (RelativeLayout) findViewById(R.id.commitJianguan);
        commitDocument = (RelativeLayout) findViewById(R.id.commitDocument);
        commitShot = (RelativeLayout) findViewById(R.id.commitShot);

        if (success == IBase.CONSTANT_ZERO) {
            commitDocument.setVisibility(View.GONE);
            mTvTitle.setText("提交文档");
            new_documentCommit.setText("新建文档");
        } else if (success == IBase.CONSTANT_ONE) {
            commitJianguan.setVisibility(View.GONE);
            mTvTitle.setText("绑定监管器");
            new_documentCommit.setText("新增绑定");
            txt_documentCommit.setText("绑定成功");
            toast_documentCommit.setText("点击返回列表查看监管器绑定信息");
            sel_documentCommit.setText("或点击新增绑定继续操作");
        } else if (success == IBase.CONSTANT_TWO) {
            commitShot.setVisibility(View.GONE);
            UploadPath = getIntent().getStringExtra("UploadPath");
            mTvTitle.setText("任务提交");
            nextList_documentCommit.setText("返回上传列表");
            new_documentCommit.setText("继续添加");
            txt_documentCommit.setText("照片采集成功");
            toast_documentCommit.setText("你返回上传列表上传资料");
            sel_documentCommit.setText("或者查看详情进行操作");
        } else if (success == IBase.CONSTANT_THREE) {
            commitNewCar.setVisibility(View.GONE);
            mTvTitle.setText("监管车辆提交");
            new_documentCommit.setText("新增车辆");
            txt_documentCommit.setText("监管车辆成功");
            toast_documentCommit.setText("点击返回列表查看监管车辆信息");
            sel_documentCommit.setText("或点击新增车辆继续操作");
        } else if (success == IBase.CONSTANT_FOUR) {
            commitBiaoqian.setVisibility(View.GONE);
            mTvTitle.setText("车辆绑标签提交");
            new_documentCommit.setText("车辆绑标签");
            txt_documentCommit.setText("车辆绑标签成功");
            toast_documentCommit.setText("点击返回列表查看标签列表");
            sel_documentCommit.setText("或点击车辆绑标签继续操作");
        }
    }

    public void nextIntent(View view) {
        if (view.getId() == R.id.nextList_documentCommit) {
            Intent intent = new Intent();
            if (success == IBase.CONSTANT_ZERO) {
                intent.setClass(this, DocumentActivity.class);
            } else if (success == IBase.CONSTANT_ONE) {
                intent.setClass(this, BindingsActivity.class);
            } else if (success == IBase.CONSTANT_TWO) {
                intent.setClass(this, UploadListActivity.class);
            } else if (success == IBase.CONSTANT_THREE) {
                intent.setClass(this, CarListActivity.class);
            } else if (success == IBase.CONSTANT_FOUR) {
                intent.setClass(this, LableActivity.class);
            }
            startActivity(intent);
            finish();
        } else if (view.getId() == new_documentCommit) {
            Intent intent = new Intent();
            if (success == IBase.CONSTANT_ZERO) {
                intent.setClass(this, DocumentNewActivity.class);
            } else if (success == IBase.CONSTANT_ONE) {
                intent.setClass(this, AddBindingActivity.class);
            } else if (success == IBase.CONSTANT_TWO) {
                intent.setClass(this, PhotosDetailsActivity.class);
//                intent.putExtra("UploadPath", UploadPath);
            } else if (success == IBase.CONSTANT_THREE) {
                intent.setClass(this, NewCarActivity.class);
            } else if (success == IBase.CONSTANT_FOUR) {
                intent.setClass(this, AddLableActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }

    public void jumpIntent(View view) {
        Intent intent = new Intent();
        if (view == commitNewCar) {
            //新建车辆
            intent.setClass(this, NewCarActivity.class);
            intent.putExtra("isCar", true);
            IBaseMethod.showToast(this, "新建车辆需要进行VIN扫描操作", IBase.RETAIL_TWO);
        } else if (view == commitBiaoqian) {
            //车辆绑标签
            intent.setClass(this, AddLableActivity.class);
            intent.putExtra("isLable", true);
            IBaseMethod.showToast(this, "车辆绑标签需要进行标签扫描操作", IBase.RETAIL_TWO);
        } else if (view == commitJianguan) {
            //绑定监管器
            intent.setClass(this, AddBindingActivity.class);
            intent.putExtra("isBind", true);
            IBaseMethod.showToast(this, "绑定监管器需要进行监管器二维码扫描操作", IBase.RETAIL_TWO);
        } else if (view == commitDocument) {
            //文档绑定
            intent.setClass(this, DocumentNewActivity.class);
            intent.putExtra("isDoument", true);
            IBaseMethod.showToast(this, "文档绑定需要进行标签扫描操作", IBase.RETAIL_TWO);
        } else if (view == commitShot) {
            //照片采集
            intent.setClass(this, PhotosDetailsActivity.class);
        }
        startActivity(intent);
    }
}
