package com.kaopujinfu.appsys.thecar.bean;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.thecar.R;

/**
 * Created by 左丽姬 on 2017/5/19.
 */

public class DocumentHold {
    public RelativeLayout documentDetailsLayout_item;
    public TextView distributor; // 经销商
    public TextView state; // 状态
    public TextView number; // 编号
    public TextView vin; // vin码
    public ImageView item; //
    public View line;
    public RelativeLayout layout_item;
    public ProgressBar ProgressBar_item;

    public DocumentHold(View view){
        documentDetailsLayout_item = (RelativeLayout) view.findViewById(R.id.documentDetailsLayout_item);
        distributor = (TextView) view.findViewById(R.id.documentDistributor_item);
        state = (TextView) view.findViewById(R.id.documentState_item);
        number = (TextView) view.findViewById(R.id.documentNum_item);
        vin = (TextView) view.findViewById(R.id.documentVIN_item);
        item = (ImageView) view.findViewById(R.id.documentDetails_item);
        line = view.findViewById(R.id.documentLine_item);
        layout_item = (RelativeLayout) view.findViewById(R.id.Layout_item);
        ProgressBar_item = (ProgressBar) view.findViewById(R.id.ProgressBar_item);
    }
}
