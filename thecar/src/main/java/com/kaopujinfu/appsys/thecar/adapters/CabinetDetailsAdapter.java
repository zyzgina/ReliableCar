package com.kaopujinfu.appsys.thecar.adapters;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.bean.CabinetDetailsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:柜子详情
 * Created Author: Gina
 * Created Date: 2017/7/19.
 */

public class CabinetDetailsAdapter extends BaseAdapter {
    private Context context;
    private List<CabinetDetailsBean.CellsEntity> entities;
    CaDetailsHodler hodler = null;
    public CabinetDetailsAdapter(Context context) {
        this.context = context;
        this.entities = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return entities.size();
    }

    @Override
    public Object getItem(int position) {
        return entities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    float ry=0;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_cabinet_details, null);
            hodler = new CaDetailsHodler(convertView);
            convertView.setTag(hodler);
        } else {
            hodler = (CaDetailsHodler) convertView.getTag();
        }
        final CabinetDetailsBean.CellsEntity entity = entities.get(position);
        hodler.codeCabinetDetails.setText(entity.getCId());
        if (GeneralUtils.isEmpty(entity.getCContent())) {
            hodler.supCabinetDetails.setText("无监管物");
            hodler.supCabinetDetails.setBackgroundColor(context.getResources().getColor(R.color.gray_light));
            hodler.vinCabinetDetails.setText("");
            hodler.brandCabinetDetails.setText("");
        } else if (entity.getCContent().indexOf("]") == entity.getCContent().length() - 1) {
            hodler.vinCabinetDetails.setVisibility(View.VISIBLE);
            hodler.supCabinetDetails.setText("自动监管");
            hodler.supCabinetDetails.setBackgroundColor(context.getResources().getColor(R.color.blue_light));
            hodler.brandCabinetDetails.setText(entity.getCarBrand());
            if (!GeneralUtils.isEmpty(entity.getVinNo()))
                hodler.vinCabinetDetails.setText(entity.getVinNo().substring(entity.getVinNo().length() - 6, entity.getVinNo().length()));
            else
                hodler.vinCabinetDetails.setText("");
        } else {
            hodler.vinCabinetDetails.setVisibility(View.GONE);
            hodler.supCabinetDetails.setText("手动监管");
            hodler.supCabinetDetails.setBackgroundColor(context.getResources().getColor(R.color.check_ok));
            hodler.brandCabinetDetails.setText(entity.getCContent());
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            hodler.brandCabinetDetails.measure(w,h);
            final int sizeWidth=hodler.brandCabinetDetails.getMeasuredWidth();
            if(sizeWidth>450) {
                hodler.brandCabinetDetails.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                ry=event.getRawY();
                                if (listenis != null)
                                    listenis.onCabinetTocuhListenis(position, true, event.getRawX(), event.getRawY());
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if(ry-event.getRawY()>=10||event.getRawY()-ry>=10){
                                    if (listenis != null)
                                        listenis.onCabinetTocuhListenis(position, false, event.getRawX(), event.getRawY());
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                if (listenis != null)
                                    listenis.onCabinetTocuhListenis(position, false, event.getRawX(), event.getRawY());
                                break;
                        }
                        return true;
                    }
                });
            }
        }
        return convertView;
    }

    class CaDetailsHodler {
        TextView codeCabinetDetails;
        TextView supCabinetDetails;
        TextView brandCabinetDetails;
        TextView vinCabinetDetails;

        public CaDetailsHodler(View view) {
            codeCabinetDetails = (TextView) view.findViewById(R.id.codeCabinetDetails);
            supCabinetDetails = (TextView) view.findViewById(R.id.supCabinetDetails);
            brandCabinetDetails = (TextView) view.findViewById(R.id.brandCabinetDetails);
            vinCabinetDetails = (TextView) view.findViewById(R.id.vinCabinetDetails);
        }
    }

    public void clearCabinetDetails() {
        this.entities.clear();
        notifyDataSetChanged();
    }

    public void addCabinetDetails(List<CabinetDetailsBean.CellsEntity> entities) {
        this.entities.addAll(entities);
        notifyDataSetChanged();
    }

    private CabinetTocuhListenis listenis;
    public interface CabinetTocuhListenis{
        void onCabinetTocuhListenis(int poistion,boolean isVisibility,float x,float y);
    }
    public void setOnCabinetTocuhListenis(CabinetTocuhListenis listenis){
        this.listenis=listenis;
    }

}
