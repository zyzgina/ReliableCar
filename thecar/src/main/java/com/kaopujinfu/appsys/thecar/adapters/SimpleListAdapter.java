package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.thecar.R;


/**
 * 侧滑菜单列表
 * Created by zuoliji on 2017/3/29.
 */

public class SimpleListAdapter extends BaseAdapter {
    private String[] strs = {"修改密码", "当前版本"};
    private int[] ints = {R.drawable.simple_lists_updatepaw_drawable, R.drawable.simple_lists_version_drawable};
    private Context context;

    public SimpleListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public Object getItem(int i) {
        return strs[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentview, ViewGroup viewGroup) {
        if (contentview == null)
            contentview = LayoutInflater.from(context).inflate(R.layout.item_simple_list, null);
        ImageView image_simple_list = (ImageView) contentview.findViewById(R.id.image_simple_list);
        TextView text_simple_list = (TextView) contentview.findViewById(R.id.text_simple_list);
        TextView text_verson = (TextView) contentview.findViewById(R.id.text_verson);
        if (i == 0) {
            text_verson.setVisibility(View.GONE);
        } else {
            text_verson.setText(IBaseMethod.getVersion(context));
        }
        image_simple_list.setImageResource(ints[i]);
        text_simple_list.setText(strs[i]);
        return contentview;
    }
}
