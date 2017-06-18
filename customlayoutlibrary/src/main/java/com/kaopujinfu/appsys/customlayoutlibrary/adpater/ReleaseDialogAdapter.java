package com.kaopujinfu.appsys.customlayoutlibrary.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.ReleaseIcno;

import java.util.List;


/**
 * Created by 左丽姬 on 2017/5/12.
 */

public class ReleaseDialogAdapter extends BaseAdapter {
    private Context context;
    private List<ReleaseIcno> icnos;

    public ReleaseDialogAdapter(Context context, List<ReleaseIcno> icnos) {
        this.context = context;
        this.icnos = icnos;
    }

    @Override
    public int getCount() {
        return icnos.size();
    }

    @Override
    public Object getItem(int position) {
        return icnos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OperationsHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tools, null);
            holder = new OperationsHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (OperationsHolder) convertView.getTag();
        }
        holder.image_tools_item.setImageResource(icnos.get(position).getIcon());
        holder.text_tools_item.setText(icnos.get(position).getName());
        return convertView;
    }

    class OperationsHolder {
        ImageView image_tools_item;
        TextView text_tools_item, num_tools_item;
        View view_tools_line;
        LinearLayout ll_tools;

        public OperationsHolder(View view) {
            image_tools_item = (ImageView) view.findViewById(R.id.image_tools_item);
            text_tools_item = (TextView) view.findViewById(R.id.text_tools_item);
            num_tools_item = (TextView) view.findViewById(R.id.num_tools_item);
            view_tools_line = view.findViewById(R.id.view_tools_line);
            ll_tools = (LinearLayout) view.findViewById(R.id.ll_tools);
        }
    }
}
