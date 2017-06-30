package com.kaopujinfu.appsys.thecar.myselfs.newcar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.bean.DistributorGpsBean;
import com.kaopujinfu.appsys.customlayoutlibrary.dialog.LoadingDialog;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.LoactionListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseUrl;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MapUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.RealIconView;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.BusinessAdapter;
import com.kaopujinfu.appsys.thecar.adapters.DistributorGpsAdapter;
import com.kaopujinfu.appsys.thecar.bean.BusinessBean;

import net.tsz.afinal.FinalDb;

import java.util.List;

/**
 * Describe: 经销商列表
 * Created Author: Gina
 * Created Date: 2017/6/14.
 */

public class DistributorActivity extends Activity implements View.OnClickListener {
    private ImageView mBackDistrbutor;
    private Button mSearchDistrbutor;
    private EditText editTextDistrbutor;
    private ExpandableListView expandableListViewDistrbutor;
    private ListView listViewDistrbutor;
    private MapUtils mapUtils;
    private double longitude, latitude;
    private LoadingDialog dialog;
    private LinearLayout mNodata;
    private FinalDb db;
    private DistributorGpsAdapter mGpsAdapter;
    private BusinessAdapter mAdapter;
    private RealIconView realiconviewDistrbutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
        mapUtils = new MapUtils(this);
        initView();
        mapUtils.initOnceLocation();
        mapUtils.startLocation(new LoactionListener() {
            @Override
            public void getOnLoactionListener(double longitude, double latitude) {
                DistributorActivity.this.longitude = longitude;
                DistributorActivity.this.latitude = latitude;
                LogUtils.debug("longitude=" + longitude + " latitude=" + latitude);
                initData();
                mapUtils.stopLocation();
            }
        });
    }

    private void initView() {
        dialog = new LoadingDialog(this);
        dialog.dismiss();
//        dialog.setLoadingTitle("正在加载...");
        db = FinalDb.create(this, IBase.BASE_DATE, true);
        mBackDistrbutor = (ImageView) findViewById(R.id.backDistrbutor);
        mBackDistrbutor.setOnClickListener(this);
        LinearLayout mTopDistrbutor = (LinearLayout) findViewById(R.id.topDistrbutor);
        mTopDistrbutor.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        mSearchDistrbutor = (Button) findViewById(R.id.searchDistrbutor);
        mSearchDistrbutor.setOnClickListener(this);
        editTextDistrbutor = (EditText) findViewById(R.id.editTextDistrbutor);
        editTextDistrbutor.addTextChangedListener(textWatcher);
        expandableListViewDistrbutor = (ExpandableListView) findViewById(R.id.expandableListViewDistrbutor);
        realiconviewDistrbutor = (RealIconView) findViewById(R.id.realiconviewDistrbutor);
        realiconviewDistrbutor.setFlag(true);
        mGpsAdapter = new DistributorGpsAdapter(this, expandableListViewDistrbutor);
        IBaseMethod.setBaseInfo(this);
        final List<DistributorGpsBean.GpsEntity> gpsEntities = db.findAllByWhere(DistributorGpsBean.GpsEntity.class, "loginId=\"" + IBase.USERID + "\" order by data desc");
        if (gpsEntities.size() > 0)
            mGpsAdapter.addLists("近期使用", gpsEntities);
        mGpsAdapter.setOnChildClickListener(new DistributorGpsAdapter.ChildClickListener() {
            @Override
            public void onChildClickListener(View view, int groupPosition, int childPosition) {
                DistributorGpsBean.GpsEntity gpsEntity = mGpsAdapter.getClikData(groupPosition, childPosition);
                selBack(gpsEntity);
            }
        });
        expandableListViewDistrbutor.setAdapter(mGpsAdapter);
        mGpsAdapter.notifyDataSetChanged();

        mAdapter = new BusinessAdapter(this);
        listViewDistrbutor = (ListView) findViewById(R.id.listViewDistrbutor);
        listViewDistrbutor.setAdapter(mAdapter);
        listViewDistrbutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BusinessBean.ItemsBean itemsBean = mAdapter.getBusinessBean(position);
                DistributorGpsBean.GpsEntity gpsEntity = new DistributorGpsBean.GpsEntity();
                gpsEntity.setDist("");
                gpsEntity.setName(itemsBean.getLongName());
                gpsEntity.setDlr(itemsBean.getCompanyCode());
                selBack(gpsEntity);
            }
        });
        mNodata = (LinearLayout) findViewById(R.id.ll_nodate);
        mNodata.setVisibility(View.GONE);
        TextView mNoDateTv = (TextView) findViewById(R.id.no_date_text);
        mNoDateTv.setText("未找到经销商");
    }

    @Override
    public void onClick(View v) {
        if (v == mBackDistrbutor) {
            finish();
        } else if (v == mSearchDistrbutor) {
            //搜索
            String search = mSearchDistrbutor.getText().toString();
            if ("搜 索".equals(search)) {
                dialog.show();
                dialog.setLoadingTitle("正在搜索...");
                searchData();
            } else {
                mSearchDistrbutor.setVisibility(View.GONE);
                mBackDistrbutor.setVisibility(View.VISIBLE);
                expandableListViewDistrbutor.setVisibility(View.VISIBLE);
                listViewDistrbutor.setVisibility(View.GONE);
            }
        }
    }

    /* 搜索 */
    private void searchData() {
        String editText = editTextDistrbutor.getText().toString();
        HttpBank.getIntence(this).getDlrList(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug("搜索===获取经销商:" + o.toString());
                BusinessBean businessBean = BusinessBean.getBusiness(o.toString());
                if (businessBean != null) {
                    mAdapter.setBusinessBean(businessBean);
                    mNodata.setVisibility(View.GONE);
                    expandableListViewDistrbutor.setVisibility(View.GONE);
                    listViewDistrbutor.setVisibility(View.VISIBLE);
                } else {
                    mNodata.setVisibility(View.VISIBLE);
                    listViewDistrbutor.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
            }
        }, IBaseUrl.ACTION_COMPANY, editText);
    }

    /* 获取经销商分组列表 */
    private void initData() {
        HttpBank.getIntence(this).getDlrList(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                realiconviewDistrbutor.setFlag(false);
                realiconviewDistrbutor.setVisibility(View.GONE);
                LogUtils.debug("获取经销商:" + o.toString());
                DistributorGpsBean gpsBean = DistributorGpsBean.getDistributorGpsBean(o.toString());
                if (gpsBean != null) {
                    if (gpsBean.getIn1KM() != null && gpsBean.getIn1KM().size() > 0) {
                        mGpsAdapter.addLists("1000米内", gpsBean.getIn1KM());
                    }
                    if (gpsBean.getOther() != null && gpsBean.getOther().size() > 0) {
                        mGpsAdapter.addLists("其他", gpsBean.getOther());
                    }
                    mNodata.setVisibility(View.GONE);
                    expandableListViewDistrbutor.setVisibility(View.VISIBLE);
                    mGpsAdapter.notifyDataSetChanged();
                    if (mGpsAdapter.getGroupCount() == 0) {
                        mNodata.setVisibility(View.VISIBLE);
                        expandableListViewDistrbutor.setVisibility(View.GONE);
                    }
                } else {
                    mNodata.setVisibility(View.VISIBLE);
                    expandableListViewDistrbutor.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
            }
        }, IBaseUrl.ACTION_COMPANY_GPS, longitude + "", latitude + "");
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String edittext = editTextDistrbutor.getText().toString();
            if (GeneralUtils.isEmpty(edittext)) {
                mSearchDistrbutor.setText("取 消");
            } else {
                mSearchDistrbutor.setText("搜 索");
                mNodata.setVisibility(View.GONE);
                mSearchDistrbutor.setVisibility(View.VISIBLE);
                mBackDistrbutor.setVisibility(View.GONE);
            }
        }
    };

    /* 选中保存数据并返回 */
    private void selBack(DistributorGpsBean.GpsEntity gpsEntity) {
        IBaseMethod.setBaseInfo(this);
        gpsEntity.setLoginId(IBase.USERID);
        gpsEntity.setData(DateUtil.getSimpleDateYYYYMMDDHHMMMSS(System.currentTimeMillis()));
        List<DistributorGpsBean.GpsEntity> gps = db.findAllByWhere(DistributorGpsBean.GpsEntity.class, "loginId=\"" + IBase.USERID + "\" order by data desc");
//        for (DistributorGpsBean.GpsEntity g : gps) {
//            LogUtils.debug(g.toString());
//        }
        //判断该经销商是否存在
        List<DistributorGpsBean.GpsEntity> isEs = db.findAllByWhere(DistributorGpsBean.GpsEntity.class, "loginId=\"" + IBase.USERID + "\" and dlr=\"" + gpsEntity.getDlr() + "\"");
        if (isEs.size() > 0) {
            gpsEntity.setId(isEs.get(0).getId());
            db.update(gpsEntity);
        } else {
            if (gps.size() > 2) {
                //删除最老的一条数据
                db.delete(gps.get(0));
            }
            db.save(gpsEntity);
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("gpsEntity", gpsEntity);
        intent.putExtras(bundle);
        setResult(IBase.RESUTL_ZERO, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realiconviewDistrbutor.setFlag(false);
    }
}
