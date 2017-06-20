package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.ContinuityCameraActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogCameraListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.PicassoImageLoader;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogTxt;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.NoCacheViewPager;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ShowPhototsAdapter;
import com.kaopujinfu.appsys.thecar.adapters.UploadTaskAdapter;

import java.util.List;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * Created by 左丽姬 on 2017/5/22.
 */

public class UploadTaskActivity extends BaseNoScoActivity implements View.OnClickListener {
    private String type;
    private MyGridView uploadTaskGridView;
    private TextView uploadTasknum;
    private LinearLayout uploadTaskConfirm;
    private UploadTaskAdapter mAdapter;
    private String fileName;
    private final int REQUEST_CODE_GALLERY = 1001;
    private RelativeLayout showPhotos;
    boolean flag = false;
    private NoCacheViewPager viewpage_showPhotots;
    private LinearLayout delete_showPhotos, replace_showPhotos;
    private ShowPhototsAdapter sAdapter;
    private TextView size_showPhotots;
    private int index;
    String path;
    private TextView confirmTv;
    private int repceStatus = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadtask);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        type = getIntent().getStringExtra("type");
        String pathFile = getIntent().getStringExtra("path");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mTvTitle.setText(type);
        top_btn.setText("保存");
        top_btn.setVisibility(View.VISIBLE);
        top_meun.setVisibility(View.GONE);
        top_btn.setOnClickListener(this);
        mtop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });

        uploadTaskGridView = (MyGridView) findViewById(R.id.uploadTaskGridView);
        mAdapter = new UploadTaskAdapter(this, type, pathFile);
        uploadTaskGridView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        uploadTasknum = (TextView) findViewById(R.id.uploadTasknum);
        uploadTaskConfirm = (LinearLayout) findViewById(R.id.uploadTaskConfirm);
        confirmTv = (TextView) findViewById(R.id.confirmTv);
        uploadTaskConfirm.setOnClickListener(this);

        /* 图片预览 */
        showPhotos = (RelativeLayout) findViewById(R.id.showPhotos);
        showPhotos.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        ImageView Back_showPhotos = (ImageView) findViewById(R.id.Back_showPhotos);
        Back_showPhotos.setOnClickListener(this);
        TextView title_showPhotos = (TextView) findViewById(R.id.title_showPhotos);
        title_showPhotos.setText(type);
        size_showPhotots = (TextView) findViewById(R.id.size_showPhotots);

        viewpage_showPhotots = (NoCacheViewPager) findViewById(R.id.viewpage_showPhotots);
        delete_showPhotos = (LinearLayout) findViewById(R.id.delete_showPhotos);
        replace_showPhotos = (LinearLayout) findViewById(R.id.replace_showPhotos);
        delete_showPhotos.setOnClickListener(this);
        replace_showPhotos.setOnClickListener(this);

        sAdapter = new ShowPhototsAdapter(this);
        sAdapter.addImages(mAdapter.getLists());
        viewpage_showPhotots.setAdapter(sAdapter);


        path = pathFile + "/" + type + "/";//拍照保存的路径
        LogUtils.debug("保存照片的地址:" + path);
        uploadTaskGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == (mAdapter.getCount() - 1)) {
                    size = 8;
                    selecotPhotos();
                } else {
                    if (GeneralUtils.isEmpty(mAdapter.getPath(position))) {
                        IBaseMethod.showToast(UploadTaskActivity.this, "尚未添加图片", IBase.RETAIL_TWO);
                        return;
                    }
                    flag = true;
                    showPhotos.setVisibility(View.VISIBLE);
                    header.setVisibility(View.GONE);
                    IBaseMethod.setBarStyle(UploadTaskActivity.this, getResources().getColor(R.color.transparent));
                    size_showPhotots.setText((position + 1) + "/" + sAdapter.getCount());
                    viewpage_showPhotots.setCurrentItem(position);
                    index = position;
                }
            }
        });
        viewpage_showPhotots.setOnPageChangeListener(new NoCacheViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                index = position;
                size_showPhotots.setText((position + 1) + "/" + sAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    int size = 8;

    private void selectPic() {
        PicassoImageLoader imageLoader = new PicassoImageLoader();
        FunctionConfig functionConfig = new FunctionConfig.Builder().setMutiSelectMaxSize(size).build();
        ThemeConfig themeConfig = new ThemeConfig.Builder()
                // 标题栏背景颜色
                .setTitleBarBgColor(getResources().getColor(R.color.colorPrimaryDark))
                // 标题栏文本字体颜色
                .setTitleBarTextColor(Color.WHITE)
                // 标题栏icon颜色，如果设置了标题栏icon，设置setTitleBarIconColor将无效
                .setTitleBarIconColor(Color.WHITE)
                // 设置Floating按钮Nornal状态颜色
                .setFabNornalColor(getResources().getColor(R.color.car_theme))
                // 设置Floating按钮Pressed状态颜色
                .setFabPressedColor(getResources().getColor(R.color.blue))
                // 选择框未选颜色
                .setCheckNornalColor(Color.WHITE)
                // 选择框选中颜色
                .setCheckSelectedColor(getResources().getColor(R.color.car_theme))
                //设置标题栏清除选择按钮
                .setIconClear(R.drawable.cancel_bluetooth)
                //设置返回按钮icon
                .setIconBack(R.drawable.back)
                // 设置预览按钮icon
                .setIconPreview(R.drawable.ic_preview)
                .build();
        CoreConfig config = new CoreConfig.Builder(UploadTaskActivity.this, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig).build();
        GalleryFinal.init(config);
        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
    }


    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                if (size != 1) {
                    mAdapter.addImages(resultList);
                    mAdapter.notifyDataSetChanged();
                    sAdapter.addImages(mAdapter.getLists());
                    sAdapter.notifyDataSetChanged();
                    uploadTasknum.setText("已选择" + mAdapter.selectImages.size() + "个任务，等待上传至上传队列");
                } else {
                    int indexc = viewpage_showPhotots.getCurrentItem();
                    LogUtils.debug("选择的下标:" + indexc);
                    mAdapter.repace(indexc, resultList);
                    mAdapter.notifyDataSetChanged();
                    sAdapter.addImages(mAdapter.getLists());
                    sAdapter.notifyDataSetChanged();
                }

            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(UploadTaskActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_ZERO) {
            // 从相机中选择
            mAdapter.notifyDataSetChanged();
        }
        if (requestCode == IBase.RETAIL_ONE) {
            // 拍照
            mAdapter.notifyDataSetChanged();
            sAdapter.addImages(mAdapter.getLists());
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (!flag) {
            if (mAdapter.getSelectImages().size() == 0) {
                setResult(IBase.RESUTL_THREE);
                finish();
                return;
            }
            DialogUtil.prompt(this, "退出将会删除现下的所有照片和信息，您确定退出？",
                    "取消", "确定", new DialogButtonListener() {
                        @Override
                        public void ok() {
                            mAdapter.exit();
                            finish();
                        }

                        @Override
                        public void cancel() {

                        }
                    });
        } else {
            showPhotos.setVisibility(View.GONE);
            flag = false;
            header.setVisibility(View.VISIBLE);
            IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        }
    }

    boolean isUpload = false;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.top_btn) {
            if (sAdapter.getCount() == 0) {
                IBaseMethod.showToast(this, "您未选择上传图片，赶快去选择吧！", IBase.RETAIL_TWO);
                return;
            }
            mAdapter.saveUploadList(handler, 0);
        } else if (v.getId() == R.id.delete_showPhotos) {
            showdelRepDialog(0);
        } else if (v.getId() == R.id.replace_showPhotos) {
            showdelRepDialog(1);
        } else if (v.getId() == R.id.Back_showPhotos) {
            flag = false;
            showPhotos.setVisibility(View.GONE);
            header.setVisibility(View.VISIBLE);
            IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        } else if (v.getId() == R.id.uploadTaskConfirm) {
            //上传
            LogUtils.debug("点击上传:" + isUpload);
            if (sAdapter.getCount() == 0) {
                IBaseMethod.showToast(this, "您未选择上传图片，赶快去选择吧！", IBase.RETAIL_TWO);
                return;
            }
            uploadPhotos();
        }
    }

    public int getCurrentPagerIdx() {
        return index;
    }

    /* 删除更改对话框 */
    private void showdelRepDialog(final int status) {
        String title = "确定删除该图片?";
        if (status == 1) {
            repacePhotots();
            return;
        }
        DialogUtil.prompt(this, title,
                "取消", "确定", new DialogButtonListener() {
                    @Override
                    public void ok() {
                        deletePhotos();
                    }

                    @Override
                    public void cancel() {

                    }
                });
    }

    /* 删除照片 */
    private void deletePhotos() {
        int indexc = viewpage_showPhotots.getCurrentItem();
        mAdapter.delete(indexc);
        viewpage_showPhotots.removeView(sAdapter.getImageview(indexc));
        sAdapter.delete(indexc);
        int num = 0;
        uploadTasknum.setText("已选择" + mAdapter.selectImages.size() + "个任务，等待上传至上传队列");
        if (sAdapter.getCount() > 0) {
            if (indexc == 0)
                num = sAdapter.getCount() - 1;
            viewpage_showPhotots.setCurrentItem(num);
            if (sAdapter.getCount() == 1) {
                size_showPhotots.setText((num + 1) + "/" + sAdapter.getCount());
                sAdapter = new ShowPhototsAdapter(UploadTaskActivity.this);
                viewpage_showPhotots.setAdapter(sAdapter);
                sAdapter.addImages(mAdapter.getLists());
            }
        } else {
            flag = false;
            showPhotos.setVisibility(View.GONE);
            header.setVisibility(View.VISIBLE);
            IBaseMethod.setBarStyle(UploadTaskActivity.this, getResources().getColor(R.color.car_theme));
        }
    }

    /* 更改照片 */
    private void repacePhotots() {
        if (mAdapter.isUploadDate(viewpage_showPhotots.getCurrentItem())) {
            IBaseMethod.showToast(this, "该照片已经上传至队列，无法更换", IBase.RETAIL_TWO);
            return;
        }
        size = 1;
        selecotPhotos();
    }

    /* 选择照片 */
    private void selecotPhotos() {
        fileName = System.currentTimeMillis() + ".jpg";
        if (size == 1) {
            repceStatus = 1;
        } else {
            repceStatus = 0;
        }
        DialogUtil.selectPicDialog(UploadTaskActivity.this, fileName, repceStatus, new DialogCameraListener() {
            @Override
            public boolean takePhoto() {
                LogTxt.getInstance().writeLog("跳转到拍照界面");
                Intent intent = new Intent(UploadTaskActivity.this, ContinuityCameraActivity.class);
                intent.putExtra("imagePath", path);
                intent.putExtra("repceStatus", repceStatus);
                startActivityForResult(intent, IBase.RETAIL_ONE);
                return false;
            }

            @Override
            public boolean selectImage() {
                LogTxt.getInstance().writeLog("跳转到图片多选界面");
                selectPic();
                return false;
            }
        });
    }

    /* 上传图片到上传队列 */
    private void uploadPhotos() {
        if (!isUpload) {
            isUpload = true;
            //开始上传
            confirmTv.setText("取消");
            confirmTv.setTextColor(getResources().getColor(R.color.car_theme));
            Drawable drawable = getResources().getDrawable(R.drawable.icon_cancel);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            confirmTv.setCompoundDrawables(drawable, null, null, null);
            mAdapter.saveUploadList(handler, 1);
        } else {
            isUpload = false;
            //取消上传
            confirmTv.setText("确认");
            confirmTv.setTextColor(getResources().getColor(R.color.orange));
            Drawable drawable = getResources().getDrawable(R.drawable.icon_confirm);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            confirmTv.setCompoundDrawables(drawable, null, null, null);
        }
        confirmTv.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.common_measure_10sp));
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mAdapter.removeAll();
                    sAdapter.addImages(mAdapter.getLists());
                    uploadTasknum.setText("已选择" + mAdapter.selectImages.size() + "个任务，等待上传至上传队列");
                    setResult(IBase.RESUTL_THREE);
                    finish();
                    break;
                case 1:
                    isUpload = false;
                    //取消上传
                    mAdapter.removeAll();
                    sAdapter.addImages(mAdapter.getLists());
                    uploadTasknum.setText("已选择" + mAdapter.selectImages.size() + "个任务，等待上传至上传队列");
                    confirmTv.setText("确认");
                    confirmTv.setTextColor(getResources().getColor(R.color.orange));
                    Drawable drawable = getResources().getDrawable(R.drawable.icon_confirm);
                    // 这一步必须要做,否则不会显示.
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    confirmTv.setCompoundDrawables(drawable, null, null, null);
                    confirmTv.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.common_measure_10sp));
                    IBaseMethod.showToast(UploadTaskActivity.this, "文件已存入上传队列，赶快去上传吧!", IBase.RETAIL_TWO);
                    break;
            }
        }
    };
}
