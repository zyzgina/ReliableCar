package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.adpater.SpinnerListAdapter;
import com.kaopujinfu.appsys.thecar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 * Created Author: Gina
 * Created Date: 2017/7/26.
 */

public class ColorsAdapter extends BaseAdapter implements SpinnerListAdapter {
    private Context context;
    private List<String> colors;
    private List<String> tempList;
    public ColorsAdapter(Context context) {
        this.context = context;
        colors=new ArrayList<>();
        String[] cols=this.context.getResources().getStringArray(R.array.colorsCar);
        for(int i=0;i<cols.length;i++){
            colors.add(cols[i]);
        }
        this.tempList=this.colors;
    }

    @Override
    public int getCount() {
        return colors.size();
    }

    @Override
    public Object getItem(int position) {
        return colors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup parent) {
        if (contentView == null)
            contentView = LayoutInflater.from(context).inflate(R.layout.item_textview, null);
        TextView tv_title = (TextView) contentView.findViewById(R.id.textview);
        tv_title.setText(colors.get(position));
        return contentView;
    }

    @Override
    public List<String> getTitles() {
        return tempList;
    }

    @Override
    public void setTitles(List<String> titles) {
        if(titles==null){
            colors=tempList;
        }else{
            colors=titles;
        }
    }

}
