package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.TextProgressBar;
import com.kaopujinfu.appsys.thecar.R;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 图片上传
 * Created by 左丽姬 on 2017/4/6.
 */

public class UploadTaskAdapter extends BaseAdapter {
    private static Context context;
    private List<String> lists;
    public List<String> selectImages = new ArrayList<>();
    private List<String> photoImages = new ArrayList<>();
    private File file;
    public List<ImageView> views = new ArrayList<>();
    private String type;
    private String path;

    public UploadTaskAdapter(Context context, String type,String path) {
        this.context = context;
        this.type = type;
        this.path=path;
        lists = new ArrayList<>();
        file = new File(path+"/"+type);
        LogUtils.debug("文件夹:"+file.getAbsolutePath());
        for (int i = 0; i < 5; i++) {
            lists.add("");
        }
    }

    @Override
    public int getCount() {
        return lists.size() + 1;
    }

    @Override
    public String getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UploadTaskHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_upload, null);
            holder = new UploadTaskHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (UploadTaskHolder) convertView.getTag();
        }
        holder.name_item_upload.setVisibility(View.GONE);
        if (position == lists.size()) {
            holder.default_item_upload.setImageResource(R.drawable.pre_results_icon_increase);
            holder.defaultText_item_upload.setVisibility(View.GONE);
            holder.default_item_upload.setVisibility(View.VISIBLE);
            holder.upload_item_upload.setVisibility(View.GONE);
            holder.textProgressBar_upload.setVisibility(View.GONE);
        } else {
            holder.default_item_upload.setVisibility(View.GONE);
            if (GeneralUtils.isEmpty(lists.get(position))) {
                holder.defaultText_item_upload.setText(position + 1 + "");
                holder.defaultText_item_upload.setVisibility(View.VISIBLE);
                holder.upload_item_upload.setVisibility(View.GONE);
                holder.textProgressBar_upload.setVisibility(View.GONE);
            } else if (photoImages.contains(lists.get(position))) {
                holder.defaultText_item_upload.setVisibility(View.GONE);
                holder.textProgressBar_upload.isText(true);
                holder.textProgressBar_upload.setText("已在上传队列等待");
                holder.textProgressBar_upload.setProgress(100);
                holder.textProgressBar_upload.setVisibility(View.VISIBLE);
                holder.upload_item_upload.setVisibility(View.GONE);
                holder.upload_item_upload.setVisibility(View.VISIBLE);
                FinalBitmap.create(context).display(holder.upload_item_upload, getItem(position));
            } else {
                holder.defaultText_item_upload.setVisibility(View.GONE);
                holder.textProgressBar_upload.isText(true);
                holder.textProgressBar_upload.setText("等待上传至队列");
                holder.textProgressBar_upload.setProgress(0);
                holder.textProgressBar_upload.setVisibility(View.VISIBLE);
                holder.upload_item_upload.setVisibility(View.GONE);
                holder.upload_item_upload.setVisibility(View.VISIBLE);
                FinalBitmap.create(context).display(holder.upload_item_upload, getItem(position));
                views.add(holder.upload_item_upload);
            }
        }
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        initData();
        super.notifyDataSetChanged();
    }

    class UploadTaskHolder {
        TextProgressBar textProgressBar_upload;
        ImageView upload_item_upload;
        ImageView default_item_upload;
        TextView name_item_upload, defaultText_item_upload;

        public UploadTaskHolder(View view) {
            textProgressBar_upload = (TextProgressBar) view.findViewById(R.id.textProgressBar_upload);
            upload_item_upload = (ImageView) view.findViewById(R.id.upload_item_upload);
            default_item_upload = (ImageView) view.findViewById(R.id.default_item_upload);
            name_item_upload = (TextView) view.findViewById(R.id.name_item_upload);
            defaultText_item_upload = (TextView) view.findViewById(R.id.defaultText_item_upload);
        }
    }

    public void addImages(List<PhotoInfo> infos) {
        for (PhotoInfo photo : infos) {
            if (!selectImages.contains(photo.getPhotoPath())) {
                selectImages.add(photo.getPhotoPath());
            }
        }
        notifyDataSetChanged();
    }

    /* 获取文档地址 */
    public String getPath(int position) {
        return lists.get(position);
    }

    public void exit() {
        FileUtils.delFolder(file.getAbsolutePath());
    }

    private void initData() {
        if (!file.exists()) {
            file.mkdirs();
        }
        photoImages.clear();
        for (String image : file.list()) {
            photoImages.add(file.getAbsolutePath() + "/" + image);
        }
        lists.clear();
        lists.addAll(photoImages);
        lists.addAll(selectImages);
        shows.clear();
        shows.addAll(photoImages);
        shows.addAll(selectImages);
        if (lists.size() < 5) {
            for (int i = lists.size(); i < 5; i++) {
                lists.add("");
            }
        }

    }

    /* 删除图片 */
    public void delete(int position) {
        views.remove(position);
        if (selectImages.contains(shows.get(position)))
            selectImages.remove(position);
        else {
            FileUtils.deleteFile(photoImages.get(position));
        }

        notifyDataSetChanged();
    }

    /* 更换图片 */
    public void repace(int position, List<PhotoInfo> infos) {
        if (selectImages.contains(shows.get(position))) {
            selectImages.remove(position);
            selectImages.add(position, infos.get(0).getPhotoPath());
        } else {
            //替换保存图片
            LogUtils.debug("替换上传队列的图片:");
            View view = views.get(position);
            ImageView upload_item_upload = (ImageView) view.findViewById(R.id.upload_item_upload);
            FileUtils.saveBitMap(photoImages.get(position), ((BitmapDrawable) upload_item_upload.getDrawable()).getBitmap());
        }
        notifyDataSetChanged();
    }

    private List<String> shows = new ArrayList<>();

    public List<String> getLists() {
        return shows;
    }

    /* 获取加入上传列表的数据 */
    public List<String> getSelectImages(){
        return selectImages;
    }

    public void removeAll() {
        selectImages.clear();
        notifyDataSetChanged();
    }

    /* 保存文件到上传队列 */
    public void saveUploadList(final Handler handler, final int state) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < selectImages.size(); i++) {
                    File upload = new File(selectImages.get(i));
                    String name = type + "_" + DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance())+"_"+(i+1) + ".jpg";
                    File save = new File(file.getAbsolutePath(), name);
                    if (upload.exists())
                        FileUtils.CopySdcardFile(upload.getAbsolutePath(), save.getAbsolutePath());
                }
                handler.sendEmptyMessage(state);
            }
        }.start();
    }

}
