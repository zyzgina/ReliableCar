package com.kaopujinfu.appsys.thecar.upload;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.UploadListAdapter;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.persistent.FileRecorder;

import java.io.File;


/**
 * 上传列表
 * Created by Doris on 2017/5/15.
 */
public class UploadListActivity extends BaseNoScoActivity {

    private static final String uploadPath = FileUtils.getCarUploadPath();

    private LinearLayout noUploadList;
    private ListView uploadList;
    private UploadListAdapter mAdapter;
    private UploadManager uploadManager;
    private boolean flag = true;
    private Handler uHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IBase.CONSTANT_ONE:
                    top_btn.setVisibility(View.GONE);
                    uploadList.setVisibility(View.GONE);
                    noUploadList.setVisibility(View.VISIBLE);
                    mAdapter.delFile();
                    mAdapter.delSql();
                    flag = true;
                    break;
                case IBase.CONSTANT_TWO:
                    top_btn.setText("开始上传");
                    flag = true;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_list);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        LogTxt.getInstance().writeLog("进入上传列表页面");
    }

    @Override
    public void initView() {
        dialog.dismiss();
        top_meun.setVisibility(View.GONE);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText("上传列表");
        mAdapter = new UploadListAdapter(this, uploadPath, uHandler);
        uploadList = (ListView) findViewById(R.id.uploadList);
        uploadList.setAdapter(mAdapter);
        noUploadList = (LinearLayout) findViewById(R.id.noUploadList);
        mtop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
        if (mAdapter.getCount() > 0) {
            LogTxt.getInstance().writeLog("存在上传数据");
            top_btn.setVisibility(View.VISIBLE);
            top_btn.setText("开始上传");
            uploadList.setVisibility(View.VISIBLE);
            noUploadList.setVisibility(View.GONE);
            top_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LogTxt.getInstance().writeLog("开始上传数据");
                    if (!IBaseMethod.isNetToast(UploadListActivity.this, true)) {
                        IBaseMethod.showNetToast(UploadListActivity.this);
                        return;
                    }
                    if (flag) {
                        flag = false;
                        top_btn.setText("全部暂停");
                        mAdapter.notifyDataSetChanged();
                        mAdapter.uploadFile(uploadManager);
                    } else {
                        flag = true;
                        top_btn.setText("全部开始");
                    }
                    mAdapter.setUpload(flag);
                }
            });
        } else {
            LogTxt.getInstance().writeLog("没有上传数据");
            top_btn.setVisibility(View.GONE);
            uploadList.setVisibility(View.GONE);
            noUploadList.setVisibility(View.VISIBLE);
        }
        initData();
    }

    private void initData() {
        String dirPath = FileUtils.getLogFilePath() + "recorder";
        Recorder recorder = null;
        try {
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
            LogUtils.warn(e);
        }
        //默认使用key的url_safe_base64编码字符串作为断点记录文件的文件名
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
            }
        };
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
//                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAdapter != null)
            mAdapter.setUpload(true);
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (flag) {
            finish();
            return;
        }
        DialogUtil.prompt(this, "你正在上传文件,退出将停止上传,是否确认退出？",
                "继续上传", "退出", new DialogButtonListener() {
                    @Override
                    public void ok() {
                        finish();
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }


}
