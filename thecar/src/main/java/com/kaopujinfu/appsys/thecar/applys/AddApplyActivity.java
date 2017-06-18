package com.kaopujinfu.appsys.thecar.applys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VINactivity;
import com.kaopujinfu.appsys.customlayoutlibrary.adpater.BrandAdapter;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.BrandBean;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogItemListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.thecar.R;

import net.tsz.afinal.FinalDb;

import java.util.ArrayList;
import java.util.List;

/**
 * 新建贷款申请单
 * Created by 左丽姬 on 2017/5/18.
 */
public class AddApplyActivity extends BaseActivity implements View.OnClickListener {

    private TextView documentBrand_new, documentSubBrand_new, documentModels_new;
    private ImageView documentVINScan;
    private EditText buy_price_addaply, loanprice_addaply, documentVIN_new;

    private static FinalDb db;
    private List<BrandBean.BrandItems> brandItemses = new ArrayList<>();
    private List<BrandBean.BrandItems> subbrandItemses = new ArrayList<>();
    private List<BrandBean.BrandItems> modelsItemses = new ArrayList<>();
    private BrandAdapter brandAdapter, subBrandAdapter, modelAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addapply);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        db = FinalDb.create(this, IBase.BASE_DATE);
        mTvTitle.setText("新建贷款申请");
        top_btn.setVisibility(View.VISIBLE);
        top_meun.setVisibility(View.GONE);
        top_btn.setText("提交");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        // 改变布局
        changeLayout();
        // VIN码
        documentVIN_new = (EditText) findViewById(R.id.documentVIN_new);
        documentVINScan = (ImageView) findViewById(R.id.documentVINScan_new);
        documentVINScan.setOnClickListener(this);
        // 级联下拉框
        documentBrand_new = (TextView) findViewById(R.id.documentBrand_new);
        documentSubBrand_new = (TextView) findViewById(R.id.documentSubBrand_new);
        documentModels_new = (TextView) findViewById(R.id.documentModels_new);
        brandItemses.add(new BrandBean.BrandItems());
        brandAdapter = new BrandAdapter(this, brandItemses);
        brandAdapter.setColorStatus(IBase.CONSTANT_TWO);
        documentBrand_new.setOnClickListener(this);
        subBrandAdapter = new BrandAdapter(this, subbrandItemses);
        subBrandAdapter.setColorStatus(IBase.CONSTANT_TWO);
        documentSubBrand_new.setOnClickListener(this);
        modelAdapter = new BrandAdapter(this, modelsItemses);
        modelAdapter.setColorStatus(IBase.CONSTANT_TWO);
        documentModels_new.setOnClickListener(this);
        // 购入价
        buy_price_addaply = (EditText) findViewById(R.id.buy_price_addaply);
        // 贷款金额
        loanprice_addaply = (EditText) findViewById(R.id.loanprice_addaply);
        // 获取下拉框数据
        getBrand("", "");
    }

    @Override
    public void onClick(View v) {
        if (v == documentVINScan) {
            Intent intent = new Intent(AddApplyActivity.this, VINactivity.class);
            intent.putExtra("isScanner", true);
            startActivityForResult(intent, IBase.RETAIL_ELEVEN);
        } else if (v == documentBrand_new) {
            // 品牌
            DialogUtil.spinnerDilaog(this, brandAdapter, new DialogItemListener() {
                @Override
                public void itemListener(int position) {
                    BrandBean.BrandItems items = brandItemses.get(position);
                    getBrand(items.getBrandId(), "");
                    documentBrand_new.setText(items.getBrand());
                    documentSubBrand_new.setText("--请选择--");
                    documentModels_new.setText("--请选择--");
                }
            });
        } else if (v == documentSubBrand_new) {
            // 子品牌
            DialogUtil.spinnerDilaog(this, subBrandAdapter, new DialogItemListener() {
                @Override
                public void itemListener(int position) {
                    BrandBean.BrandItems items = subbrandItemses.get(position);
                    LogUtils.debug("======" + items.getBrandId() + "====" + items.getSubBrandId());
                    if (!GeneralUtils.isEmpty(items.getBrandId()) && !GeneralUtils.isEmpty(items.getSubBrandId()))
                        getBrand(items.getBrandId(), items.getSubBrandId());
                    documentSubBrand_new.setText(items.getSubBrand());
                    documentModels_new.setText("--请选择--");
                }
            });
        } else if (v == documentModels_new) {
            // 车型
            DialogUtil.spinnerDilaog(this, modelAdapter, new DialogItemListener() {
                @Override
                public void itemListener(int position) {
                    BrandBean.BrandItems items = modelsItemses.get(position);
                    documentModels_new.setText(items.getModel());
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_ELEVEN) {
            // VIN码
            if (data != null) {
                String vin = data.getStringExtra("result");
                if (!GeneralUtils.isEmpty(vin)) {
                    documentVIN_new.setText(vin);
                }
            }
        }
    }

    /**
     * 获取品牌、经销商
     */
    private void getBrand(final String subid, final String modles) {
        boolean isBrand = (boolean) SPUtils.get(this, "isBrand", false);
        if (isBrand) {
            List<BrandBean.BrandItems> list;
            String qury = "";
            if (GeneralUtils.isEmpty(subid) && GeneralUtils.isEmpty(modles))
                qury = "leve=0";
            else if (GeneralUtils.isEmpty(modles))
                qury = "brandId=\"" + subid + "\" and leve=1";
            else
                qury = "brandId=\"" + subid + "\" and subBrandId=\"" + modles + "\" and leve=2";
            list = db.findAllByWhere(BrandBean.BrandItems.class, qury);
            if (list.size() > 0) {
                LogUtils.debug("进入了:" + list.toString());
                setValues(list, subid, modles);
                return;
            }
        }

        HttpBank.getIntence(this).httpCat("", subid, modles, new CallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("品牌:" + o.toString());
                BrandBean brand = BrandBean.getBrandBean(o.toString());
                if (brand != null && brand.isSuccess() && brand.getItems() != null) {
                    if (brand.getItems().size() > 0)
                        IBaseMethod.saveBrand(AddApplyActivity.this, db, brand.getItems(), subid, modles);
                    setValues(brand.getItems(), subid, modles);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                IBaseMethod.implementError(AddApplyActivity.this, errorNo);
            }
        });
    }

    private void setValues(List<BrandBean.BrandItems> list, String subid, String modles) {
        if (GeneralUtils.isEmpty(subid) && GeneralUtils.isEmpty(modles)) {
            // 品牌
            brandItemses.addAll(list);
            brandAdapter.notifyDataSetChanged();
        } else if (!GeneralUtils.isEmpty(subid) && GeneralUtils.isEmpty(modles)) {
            // 子品牌
            subbrandItemses.clear();
            subbrandItemses.addAll(list);
            subBrandAdapter.notifyDataSetChanged();
            modelsItemses.clear();
        } else {
            // 车型
            modelsItemses.clear();
            modelsItemses.addAll(list);
            modelAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 改变布局
     */
    private void changeLayout() {
        View view_code = findViewById(R.id.view_code_documentNum_new);
        view_code.setVisibility(View.GONE);
        LinearLayout code_documentNum_new = (LinearLayout) findViewById(R.id.code_documentNum_new);
        code_documentNum_new.setVisibility(View.GONE);
    }
}
