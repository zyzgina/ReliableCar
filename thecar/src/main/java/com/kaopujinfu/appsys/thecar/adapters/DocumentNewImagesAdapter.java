package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.FileUtils;
import com.kaopujinfu.appsys.thecar.R;

import net.tsz.afinal.FinalBitmap;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 新建文档图片资料适配器
 * Created by Doris on 2017/5/15.
 */
public class DocumentNewImagesAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> imageList = new ArrayList<>();
    private List<String> selectImages = new ArrayList<>();
    private List<String> photoImages = new ArrayList<>();
    private File file;

    public DocumentNewImagesAdapter(Context context, String path){
        mContext = context;
        file = new File(path);
    }

    @Override
    public int getCount() {
        return imageList.size() + 1;
    }

    @Override
    public String getItem(int i) {
        return imageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_document_new_image, null);
        }
        ImageView image = (ImageView) view.findViewById(R.id.documentNewImages_item);
        if (i != getCount() -1){
            image.setBackgroundColor(Color.TRANSPARENT);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            FinalBitmap.create(mContext).display(image, getItem(i));
        } else {
            image.setBackgroundResource(R.drawable.button_frame_plain_gray);
            image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            image.setImageResource(R.drawable.pic_upload_normal);
        }
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        initData();
        super.notifyDataSetChanged();
    }

    private void initData(){
        if (!file.exists()){
            file.mkdirs();
        }
        photoImages.clear();
        for (String image : file.list()) {
            photoImages.add(file.getAbsolutePath() + "/" + image);
        }
        imageList.clear();
        imageList.addAll(photoImages);
        imageList.addAll(selectImages);
    }

    public void addImages(List<PhotoInfo> infos){
        for(PhotoInfo photo : infos){
            if (!selectImages.contains(photo.getPhotoPath())){
                 selectImages.add(photo.getPhotoPath());
            }
        }
        notifyDataSetChanged();
    }

    public void exit(){
        FileUtils.delFolder(file.getAbsolutePath());
    }

    public List<String> getLists(){
        return imageList;
    }

}
