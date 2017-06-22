package com.kaopujinfu.appsys.thecar.myselfs.files;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (success == IBase.CONSTANT_ZERO) {
            mTvTitle.setText("提交文档");
            new_documentCommit.setText("新建文档");
        } else if (success == IBase.CONSTANT_ONE) {
            mTvTitle.setText("绑定监管器");
            new_documentCommit.setText("新增绑定");
            txt_documentCommit.setText("绑定成功");
            toast_documentCommit.setText("点击返回列表查看监管器绑定信息");
            sel_documentCommit.setText("或点击新增绑定继续操作");
        } else if (success == IBase.CONSTANT_TWO) {
            UploadPath = getIntent().getStringExtra("UploadPath");
            mTvTitle.setText("任务提交");
            nextList_documentCommit.setText("返回上传列表");
            new_documentCommit.setText("继续添加");
            txt_documentCommit.setText("查看详情");
            toast_documentCommit.setText("你返回上传列表上传资料");
            sel_documentCommit.setText("或者查看详情进行操作");
        } else if (success == IBase.CONSTANT_THREE) {
            mTvTitle.setText("监管车辆提交");
            new_documentCommit.setText("新增车辆");
            txt_documentCommit.setText("监管车辆成功");
            toast_documentCommit.setText("点击返回列表查看监管车辆信息");
            sel_documentCommit.setText("或点击新增车辆继续操作");
        } else if (success == IBase.CONSTANT_FOUR) {
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
                intent.putExtra("UploadPath", UploadPath);
            } else if (success == IBase.CONSTANT_THREE) {
                intent.setClass(this, NewCarActivity.class);
            } else if (success == IBase.CONSTANT_FOUR) {
                intent.setClass(this, AddLableActivity.class);
            }
            startActivity(intent);
            finish();
        }
    }
}
