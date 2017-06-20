package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.UploadBean;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.thecar.R;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalDb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 左丽姬 on 2017/5/23.
 */

public class PhotosGridAdapter extends BaseAdapter {
    private int[] ints = {R.drawable.taskdetails_icon_1, R.drawable.taskdetails_icon_2, R.drawable.taskdetails_icon_3, R.drawable.taskdetails_icon_4,
            R.drawable.taskdetails_icon_5, R.drawable.taskdetails_icon_6, R.drawable.taskdetails_icon_7, R.drawable.taskdetails_icon_8};
    private String[] strs;
    private Context mContext;
    private File file;
    private List<UploadBean> uploadBeens = new ArrayList<>();

    public PhotosGridAdapter(Context mContext, File file) {
        this.mContext = mContext;
        this.file = file;
        strs = mContext.getResources().getStringArray(R.array.photos);
        IBaseMethod.getChangeDate(strs, mContext.getResources().getStringArray(R.array.photos_en),
                mContext.getResources().getStringArray(R.array.assessment), mContext.getResources().getStringArray(R.array.assessment_en));
    }

    public PhotosGridAdapter(Context mContext, File file, String[] strs) {
        this.mContext = mContext;
        this.file = file;
        this.strs = strs;
        ints = new int[strs.length];
        ints[0] = R.drawable.assess_icon_1;
        ints[1] = R.drawable.assess_icon_2;
        ints[2] = R.drawable.assess_icon_3;
        ints[3] = R.drawable.assess_icon_4;
        ints[4] = R.drawable.assess_icon_5;
        ints[5] = R.drawable.assess_icon_6;
        ints[6] = R.drawable.assess_icon_7;
        ints[7] = R.drawable.assess_icon_8;
        ints[8] = R.drawable.assess_icon_9;
        ints[9] = R.drawable.assess_icon_10;
        ints[10] = R.drawable.assess_icon_11;
        ints[11] = R.drawable.assess_icon_12;
        ints[12] = R.drawable.assess_icon_13;
        ints[13] = R.drawable.assess_icon_14;
        ints[14] = R.drawable.assess_icon_15;
        ints[15] = R.drawable.assess_icon_16;
        ints[16] = R.drawable.assess_icon_17;
        ints[17] = R.drawable.assess_icon_18;
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public Object getItem(int position) {
        return strs[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotosGridHodler hodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_document_new_image, null);
            hodler = new PhotosGridHodler(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (PhotosGridHodler) convertView.getTag();
        }

        hodler.image.setScaleType(ImageView.ScaleType.CENTER);
        hodler.image.setImageResource(ints[position]);
        hodler.documentNewText_item.setText(strs[position]);
        hodler.documentNewText_item.setVisibility(View.VISIBLE);
        hodler.documentNewFl_item.setVisibility(View.GONE);
        if (getSize(strs[position]) != 0) {
            FinalBitmap.create(mContext).display(hodler.image, getImage(strs[position]));
            hodler.documentNewFl_item.setVisibility(View.VISIBLE);
            hodler.documentNewNum_item.setText(getSize(strs[position]) + "张");
        }
        return convertView;
    }

    class PhotosGridHodler {
        ImageView image;
        TextView documentNewText_item, documentNewNum_item;
        FrameLayout documentNewFl_item;

        public PhotosGridHodler(View view) {
            image = (ImageView) view.findViewById(R.id.documentNewImages_item);
            documentNewText_item = (TextView) view.findViewById(R.id.documentNewText_item);
            documentNewFl_item = (FrameLayout) view.findViewById(R.id.documentNewFl_item);
            documentNewNum_item = (TextView) view.findViewById(R.id.documentNewNum_item);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        uploadBeens.clear();
    }

    /* 获取数据 */
    public String getString(int position) {
        return strs[position];
    }

    /* 显示第一张图片 */
    public String getImage(String name) {
        File getFile = new File(file.getAbsolutePath() + "/" + name);
        if (file.exists()) {
            String path = "";
            List<File> files = new ArrayList<>();
            for (File image : FileUtils.findFiles(files, getFile)) {
                LogUtils.debug("image=" + image.getAbsolutePath());
                String lable = "照片_" + name;
                if ("评估高清图".equals(name)) {
                    String pathname = image.getAbsolutePath().substring(0, image.getAbsolutePath().lastIndexOf("/"));
                    pathname = pathname.substring(pathname.lastIndexOf("/") + 1, pathname.length());
                    lable += "/" + pathname;
                }
                UploadBean uploadBean = IBaseMethod.saveUploadBean(image, "", lable);
                boolean flag = true;
                for (UploadBean upload : uploadBeens) {
                    if (upload.getLoactionPath().equals(image.getAbsolutePath())) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    uploadBeens.add(uploadBean);
            }
            for (File image : FileUtils.findFiles(files, getFile)) {
                path = image.getAbsolutePath();
                break;
            }
            LogUtils.debug("上传的数据个数:" + uploadBeens.size());
            return path;
        }
        return null;
    }

    /* 文件夹中文件的数目 */
    public int getSize(String name) {
        File getFile = new File(file.getAbsolutePath() + "/" + name);
        if (file.exists()) {
            List<File> files = new ArrayList<>();
            FileUtils.findFiles(files, getFile);
            return files.size();
        }
        return 0;
    }

    /* 保存数据 */
    public void saveDateList(String vincode) {
        FinalDb db = FinalDb.create(mContext, IBase.BASE_DATE, true);
        for (UploadBean uploadBean : uploadBeens) {
            uploadBean.setVinCode(vincode);
            db.save(uploadBean);
        }
    }

    public int getSise() {
        return uploadBeens.size();
    }

    public void exit() {
        FileUtils.delFolder(file.getAbsolutePath());
    }
}

