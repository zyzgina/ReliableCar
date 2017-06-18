package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.myselfs.photos.CarDetailsActivity;
import com.squareup.picasso.Picasso;

/**
 * 车辆详情 好车推荐 适配器
 * Created by Doris on 2017/5/25.
 */
public class CarRecommendAdapter extends BaseAdapter {

    private Context mContext;

    public CarRecommendAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CarRecommendHolder holder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_car_recommend, null);
            holder = new CarRecommendHolder(view);
            view.setTag(holder);
        } else {
            holder = (CarRecommendHolder) view.getTag();
        }
        Picasso.with(mContext).load(CarDetailsActivity.imageUrl).into(holder.image);
        return view;
    }

    class CarRecommendHolder {

        ImageView image;
        TextView name, prices, loanInfo, loanInfo1;

        public CarRecommendHolder(View view) {
            image = (ImageView) view.findViewById(R.id.carRecommendImage_item);
            name = (TextView) view.findViewById(R.id.carRecommendName_item);
            prices = (TextView) view.findViewById(R.id.carRecommendPrices_item);
            loanInfo = (TextView) view.findViewById(R.id.carRecommendLoanInfo_item);
            loanInfo1 = (TextView) view.findViewById(R.id.carRecommendLoanInfo1_item);
        }

    }
}
