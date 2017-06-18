package com.kaopujinfu.appsys.thecar.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;


/**
 * 盘库列表布局文件类
 * Created by 左丽姬 on 2017/5/15.
 */

public class ChecksDetailHodler {
    public TextView mCompanyname, mNum, mStatus, mDate, mVincode;
    public ImageView mIcon;
    public View mView;
    public LinearLayout mpk;

    public ChecksDetailHodler(View view) {
        mCompanyname = (TextView) view.findViewById(R.id.companyname_item_checkslsits);
        mNum = (TextView) view.findViewById(R.id.num_item_checkslists);
        mStatus = (TextView) view.findViewById(R.id.status_item_checkslists);
        mDate = (TextView) view.findViewById(R.id.date_item_checkslists);
        mView = view.findViewById(R.id.view_item_checkslists);
        mVincode = (TextView) view.findViewById(R.id.vincode_item_checkslists);
        mIcon = (ImageView) view.findViewById(R.id.icon_item_checkslists);
        mpk = (LinearLayout) view.findViewById(R.id.pk_item_checkslists);
    }
}
