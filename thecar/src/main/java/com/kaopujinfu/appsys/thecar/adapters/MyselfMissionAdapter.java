package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.thecar.R;


/**
 * Created by 左丽姬 on 2017/5/12.
 */

public class MyselfMissionAdapter extends BaseAdapter {

    private Context context;
    private int status= IBase.CONSTANT_ZERO;

    public MyselfMissionAdapter(Context context) {
        this.context = context;
    }
    public void setStatus(int status){
        this.status=status;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MissionHodler hodler=null;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_myself_missioin, null);
            hodler=new MissionHodler(convertView);
            convertView.setTag(hodler);
        }else{
            hodler= (MissionHodler) convertView.getTag();
        }
        if(status== IBase.CONSTANT_ZERO&&position==4){
            hodler.mView.setVisibility(View.GONE);
        }else{
            hodler.mView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class MissionHodler{
        TextView mNmae;
        TextView mDate;
        View mView;
        public MissionHodler(View view) {
            mNmae = (TextView) view.findViewById(R.id.name_mission_myself);
            mDate = (TextView) view.findViewById(R.id.date_mission_myself);
            mView = view.findViewById(R.id.view_mission_myself);
        }
    }
}
