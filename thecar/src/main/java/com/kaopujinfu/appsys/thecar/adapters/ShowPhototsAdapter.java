package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kaopujinfu.appsys.customlayoutlibrary.view.NoCacheViewPager;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.myselfs.photos.UploadTaskActivity;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 左丽姬 on 2017/5/22.
 */

public class ShowPhototsAdapter extends PagerAdapter {

    private List<String> shows = new ArrayList<>();
    private Context mContext;
    private List<ImageView> images = new ArrayList<>();

    public ShowPhototsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        container.removeView((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((NoCacheViewPager) container).addView(images.get(position), 0);
        return images.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        View view = (View)object;
        int currentPage = ((UploadTaskActivity)mContext).getCurrentPagerIdx(); // Get current page index
        if(currentPage == (Integer)view.getTag()){
            return POSITION_NONE;
        }else{
            return POSITION_UNCHANGED;
        }
//        return POSITION_NONE;
    }

    public void addImages(List<String> infos) {
        shows.clear();
        shows.addAll(infos);
        images.clear();
        int i=0;
        for (String str : shows) {
            ImageView imageView = (ImageView) LayoutInflater.from(mContext).inflate(R.layout.item_imageview, null);
            imageView.setTag(i);
            FinalBitmap.create(mContext).display(imageView, str);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            images.add(imageView);
            i++;
        }
        this.notifyDataSetChanged();
    }

    public void delete(int position) {
        if (position == images.size())
            position--;
        shows.remove(position);
        images.remove(position);
        notifyDataSetChanged();
    }

    public ImageView getImageview(int position) {
        if (position == images.size())
            position--;
        return images.get(position);
    }
}
