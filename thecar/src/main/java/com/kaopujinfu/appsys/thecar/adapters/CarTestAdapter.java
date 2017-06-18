package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * 车辆详情 质量检测 适配器
 * Created by Doris on 2017/5/25.
 */
public class CarTestAdapter extends BaseAdapter {

    private Context mContext;
    private int[][] info = {{R.drawable.preview_icon_seal_1, R.drawable.preview_icon_logo_1, R.color.red_orange},
            {R.drawable.preview_icon_seal_2, R.drawable.preview_icon_logo_2, R.color.blue_light},
            {R.drawable.preview_icon_seal_3, R.drawable.preview_icon_logo_3, R.color.green}};

    public CarTestAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public int[] getItem(int i) {
        return info[i];
    }

    @Override
    public long getItemId(int i) {
        return 1;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CarTestHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_car_test, null);
            holder = new CarTestHolder(view);
            view.setTag(holder);
        } else {
            holder = (CarTestHolder) view.getTag();
        }
        holder.qualifiedImage.setImageResource(getItem(i)[0]);
        holder.logo.setImageResource(getItem(i)[1]);
        holder.logo.setBackgroundColor(mContext.getResources().getColor(getItem(i)[2]));
        return view;
    }

    class CarTestHolder {
        ImageView qualifiedImage, logo;
        TextView detailsCheck;

        public CarTestHolder(View view) {
            qualifiedImage = (ImageView) view.findViewById(R.id.carTestQualifiedImage_item);
            logo = (ImageView) view.findViewById(R.id.carTestLogo_item);
            detailsCheck = (TextView) view.findViewById(R.id.carTestDetailsCheck_item);
        }

    }
}
