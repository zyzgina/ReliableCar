package com.kaopujinfu.appsys.thecar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Loginbean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.AvatarView;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 个人资料
 * Created by zuoliji on 2017/4/4.
 */

public class PersonalActivity extends BaseActivity {

    private EditText username_personal, idcode_personal, phone_number_personal, company_personal, email_personal;
    private AvatarView avatar_personal;

    private String filename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        setListener();
    }

    @Override
    public void initData() {
        String o = SPUtils.get(PersonalActivity.this, "loginUser", "").toString();
        Loginbean user = Loginbean.getLoginbean(o);
        if (user != null) {
            username_personal.setText(user.getName());
            idcode_personal.setText("");
            if (GeneralUtils.isEmpty(user.getMobile()))
                phone_number_personal.setText("未绑手机号");
            else
                phone_number_personal.setText(IBaseMethod.hide(user.getMobile(), 3, 6));
            company_personal.setText(user.getCompanyLongName());
            email_personal.setText(user.getEmail());
            if(!GeneralUtils.isEmpty(user.getHead_img())){
                //初始化加载中时显示的图片
                String urlPath = SPUtils.get(RetailAplication.getContext(), "domain", "").toString();
                //判断是否加了http://
                if (!urlPath.contains("http://")) {
                    urlPath = "http://" + urlPath;
                }
                HttpBank.getIntence(this).getHeadBg(avatar_personal,urlPath + user.getHead_img(),handler,R.drawable.avatar_head);
            }
        }
    }

    @Override
    public void initView() {
        mTvTitle.setText("个人资料");
        top_meun.setVisibility(View.GONE);
        top_btn.setText("保存");
        top_btn.setVisibility(View.VISIBLE);
        dialog.dismiss();
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);

        username_personal = (EditText) findViewById(R.id.username_personal);
        idcode_personal = (EditText) findViewById(R.id.idcode_personal);
        phone_number_personal = (EditText) findViewById(R.id.phone_number_personal);
        company_personal = (EditText) findViewById(R.id.company_personal);
        email_personal = (EditText) findViewById(R.id.email_personal);
        avatar_personal = (AvatarView) findViewById(R.id.avatar_personal);
    }

    private void setListener() {
        avatar_personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filename = System.currentTimeMillis() + ".jpg";
                DialogUtil.selectPicDialog(PersonalActivity.this, filename, null);
            }
        });
    }

    //图片上传
    private static String path = "";// sd路径
    private Bitmap head;// 头像Bitmap

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_ZERO) {
            // 从相机中选择
            LogUtils.debug("从相机中选择.....");
            try {
                cropPhoto(data.getData());
            } catch (NullPointerException e) {
                e.printStackTrace();// 用户点击取消操作
            }
        }
        if (requestCode == IBase.RETAIL_ONE) {
            // 拍照
            File temp = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + filename);
            if(Build.VERSION.SDK_INT<24){
                cropPhoto(Uri.fromFile(temp));// 裁剪图片
            }else{
                cropPhoto(getImageContentUri(PersonalActivity.this,temp));// 裁剪图片

            }
        }
        if (requestCode == IBase.RETAIL_TWO) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (GeneralUtils.isEmpty(extras)) {
                    LogUtils.debug("extras为空" + data);
                    Uri uri = data.getData();
                    if (uri != null) {
                        head = BitmapFactory.decodeFile(uri.getPath());
                        String path = setPicToView(head);// 保存在SD卡中
                        if (path != null) {
                            IBaseMethod.showToast(this, "图片上传成功！", IBase.RETAIL_ONE);
                            FinalBitmap finalBitmap = FinalBitmap.create(this);
                            finalBitmap.display(avatar_personal, "file://" + path);
                        }
                    }
                    return;
                }
                head = extras.getParcelable("data");
                if (head != null) {
                    /**
                     * 上传服务器代码
                     */
                    String path = setPicToView(head);// 保存在SD卡中
                    if (path != null) {
                        IBaseMethod.showToast(this, "图片上传成功！", IBase.RETAIL_ONE);
                        FinalBitmap finalBitmap = FinalBitmap.create(this);
                        finalBitmap.display(avatar_personal, "file://" + path);
                    }
                }
            }
        }
    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, IBase.RETAIL_TWO);
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID },
                MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    private String setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return null;
        }
        path = FileUtils.getCarUploadPath() + "/upload_image";
        LogUtils.debug("path==" + path);
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "/" + System.currentTimeMillis() + ".jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            File fi = new File(fileName);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

}
