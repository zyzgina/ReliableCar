package com.kaopujinfu.appsys.thecar.myselfs.newcar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.timeselection.view.TimeSelector;
import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.activitys.VINactivity;
import com.kaopujinfu.appsys.customlayoutlibrary.adpater.BrandAdapter;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.BrandBean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.DistributorGpsBean;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.Result;
import com.kaopujinfu.appsys.customlayoutlibrary.eventbus.JumpEventBus;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogItemListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.CallBack;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.ajaxparams.HttpBank;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DateUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.DialogUtil;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.GeneralUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.LogUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils;
import com.kaopujinfu.appsys.customlayoutlibrary.utils.VINutils;
import com.kaopujinfu.appsys.customlayoutlibrary.view.IMMListenerRelativeLayout;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.ColorsAdapter;
import com.kaopujinfu.appsys.thecar.bean.QueryVinBean;
import com.kaopujinfu.appsys.thecar.myselfs.files.DocumentCommitActivity;

import net.tsz.afinal.FinalDb;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.kaopujinfu.appsys.customlayoutlibrary.utils.SPUtils.get;

/**
 * Describe: 新建车辆
 * Created Author: Gina
 * Created Date: 2017/6/14.
 */

public class NewCarActivity extends BaseActivity implements View.OnClickListener {
    private TextView mDistributor, priceNewCar, goDateNewCar, caleDateNewCar, calePriceBuyNewCar, colorCar_new;
    private EditText mBrandNewCar, mSubBrandNewCar, mModuleNewCar, priceBuyNewCar, mileageNewCar, licenseplatenumberCar, parkinglotCar;
    private ImageView mDistributorNewImage, mBrandNewCarImage, goDateCarImage, colorCarImage;
    private DistributorGpsBean.GpsEntity gpsEntity;
    private EditText mVinNew;
    private ImageView mVinScan;
    private FinalDb db;
    private String subid = "", modles = "", sbid = "", mid = "";
    private BrandAdapter mAdapter;
    private List<BrandBean.BrandItems> items = new ArrayList<>();
    private int status = 0;//0品牌 1子品牌 2 车型
    private CheckBox isTwoCar;
    private LinearLayout twoCarMsgNewCar, priceLlNewCar, caleBuyLl, vinVerfiyNewCar;
    private boolean isCar;
    private ColorsAdapter colorsAdapter;

    private LinearLayout extendLayout;
    private IMMListenerRelativeLayout newCarImml;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcar);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initData() {
        //监听键盘的显示与隐藏
        newCarImml.setOnKeyboardStateChangedListener(new IMMListenerRelativeLayout.IOnKeyboardStateChangedListener() {
            @Override
            public void onKeyboardStateChanged(int state) {
                switch (state) {
                    case IMMListenerRelativeLayout.KEYBOARD_STATE_HIDE://软键盘隐藏
                        extendLayout.setVisibility(View.GONE);
                        break;
                    case IMMListenerRelativeLayout.KEYBOARD_STATE_SHOW://软键盘显示
                        extendLayout.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LogUtils.debug("判断返回的页面:" + isCar);
        if (isCar) {
            finish();
        } else {
            Intent intent = new Intent(NewCarActivity.this, CarListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void initView() {
        db = FinalDb.create(this, IBase.BASE_DATE, true);
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_btn.setText("提交");
        top_meun.setVisibility(View.GONE);
        top_btn.setVisibility(View.VISIBLE);
        top_btn.setOnClickListener(this);
        mTvTitle.setText("新增车辆");
        mtop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewCarActivity.this, CarListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        newCarImml = (IMMListenerRelativeLayout) findViewById(R.id.newCarImml);
        extendLayout = (LinearLayout) findViewById(R.id.extendLayout);
        extendLayout.setVisibility(View.GONE);

        /* 经销商 */
        mDistributor = (TextView) findViewById(R.id.distributor_new);
        mDistributor.setOnClickListener(this);
        mDistributorNewImage = (ImageView) findViewById(R.id.distributorNewImage);
        mDistributorNewImage.setOnClickListener(this);
        String name = SPUtils.get(NewCarActivity.this, IBase.USERID + "distribustorName", "").toString();
        if (!GeneralUtils.isEmpty(name)) {
            String dlr = SPUtils.get(NewCarActivity.this, IBase.USERID + "distribustorDlr", "").toString();
            gpsEntity = new DistributorGpsBean.GpsEntity();
            gpsEntity.setName(name);
            gpsEntity.setDlr(dlr);
            if (gpsEntity != null) {
                mDistributor.setText(gpsEntity.getName());
            }
        }
        /* vin */
        mVinNew = (EditText) findViewById(R.id.vin_new);
        mVinScan = (ImageView) findViewById(R.id.vinScan_new);
        mVinScan.setOnClickListener(this);
        mVinNew.addTextChangedListener(textWatcher);
        vinVerfiyNewCar = (LinearLayout) findViewById(R.id.vinVerfiyNewCar);

        mBrandNewCar = (EditText) findViewById(R.id.brandNewCar);
        mSubBrandNewCar = (EditText) findViewById(R.id.subBrandNewCar);
        mModuleNewCar = (EditText) findViewById(R.id.moduleNewCar);
        mBrandNewCarImage = (ImageView) findViewById(R.id.brandNewCarImage);
        mBrandNewCar.setOnClickListener(this);
        mSubBrandNewCar.setOnClickListener(this);
        mModuleNewCar.setOnClickListener(this);
        mBrandNewCarImage.setOnClickListener(this);

        mAdapter = new BrandAdapter(this, items);

        priceLlNewCar = (LinearLayout) findViewById(R.id.priceLlNewCar);
        priceNewCar = (TextView) findViewById(R.id.priceNewCar);
        priceBuyNewCar = (EditText) findViewById(R.id.priceBuyNewCar);
        caleBuyLl = (LinearLayout) findViewById(R.id.caleBuyLl);
        calePriceBuyNewCar = (TextView) findViewById(R.id.calePriceBuyNewCar);
        priceBuyNewCar.addTextChangedListener(priceWatcher);
        mileageNewCar = (EditText) findViewById(R.id.mileageNewCar);
        goDateNewCar = (TextView) findViewById(R.id.goDateNewCar);
        goDateNewCar.setText(DateUtil.getSimpleDateYYYYMMDD(System.currentTimeMillis()));
        goDateNewCar.setOnClickListener(this);
        goDateCarImage = (ImageView) findViewById(R.id.goDateCarImage);
        goDateCarImage.setOnClickListener(this);
        caleDateNewCar = (TextView) findViewById(R.id.caleDateNewCar);

        licenseplatenumberCar = (EditText) findViewById(R.id.licenseplatenumberCar);
        parkinglotCar = (EditText) findViewById(R.id.parkinglotCar);
        colorCar_new = (TextView) findViewById(R.id.colorCar_new);
        colorsAdapter = new ColorsAdapter(this);
        if (colorsAdapter.getCount() > 1) {
            colorCar_new.setText(colorsAdapter.getItem(1).toString());
        }
        colorCar_new.setOnClickListener(this);
        colorCarImage = (ImageView) findViewById(R.id.colorCarImage);
        colorCarImage.setOnClickListener(this);

        /* 是否是二手车 */
        isTwoCar = (CheckBox) findViewById(R.id.isTwoCar);
        twoCarMsgNewCar = (LinearLayout) findViewById(R.id.twoCarMsgNewCar);
        isTwoCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    twoCarMsgNewCar.setVisibility(View.VISIBLE);
                } else {
                    twoCarMsgNewCar.setVisibility(View.GONE);
                }
            }
        });
        isCar = getIntent().getBooleanExtra("isCar", false);
        if (isCar) {
            vinVerfiyNewCar.setVisibility(View.GONE);
            dialog.show();
            dialog.setLoadingTitle("正在进入VIN扫描,请稍等...");
            Intent intent = new Intent(NewCarActivity.this, VINactivity.class);
            intent.putExtra("isScanner", true);
            startActivityForResult(intent, IBase.RETAIL_ELEVEN);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mDistributor || v == mDistributorNewImage) {
            // 经销商
            Intent intent = new Intent(this, DistributorActivity.class);
            startActivityForResult(intent, IBase.RETAIL_NINE);
        } else if (v == mVinScan) {
            vinVerfiyNewCar.setVisibility(View.GONE);
            dialog.show();
            dialog.setLoadingTitle("正在进入VIN扫描,请稍等...");
            Intent intent = new Intent(NewCarActivity.this, VINactivity.class);
            intent.putExtra("isScanner", true);
            startActivityForResult(intent, IBase.RETAIL_ELEVEN);
        } else if (v == mBrandNewCar || v == mBrandNewCarImage) {
            //获取品牌
            status = 0;
            getBrand();
        } else if (v == goDateCarImage || v == goDateNewCar) {
            showDate();
        } else if (v == top_btn) {
            if (isCarExit) {
                commitNewCar();
            } else {
                getVinMoble(mVinNew.getText().toString());
            }
        } else if (v == colorCar_new || v == colorCarImage) {
            DialogUtil.spinnerDilaog(this, colorsAdapter, true, new DialogItemListener() {
                @Override
                public void itemListener(int position) {
                    String colorStr = colorsAdapter.getItem(position).toString();
                    colorCar_new.setText(colorStr);
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IBase.RETAIL_NINE && resultCode == IBase.RESUTL_ZERO) {
            if (data != null) {
                gpsEntity = (DistributorGpsBean.GpsEntity) data.getSerializableExtra("gpsEntity");
                if (gpsEntity != null) {
                    //记录最新一次
                    SPUtils.put(NewCarActivity.this, IBase.USERID + "distribustorName", gpsEntity.getName());
                    SPUtils.put(NewCarActivity.this, IBase.USERID + "distribustorDlr", gpsEntity.getDlr());
                    mDistributor.setText(gpsEntity.getName());
                }
            }
        }
        if (requestCode == IBase.RETAIL_ELEVEN) {
            // VIN码
            if (data != null) {
                String vin = data.getStringExtra("result");
                if (!GeneralUtils.isEmpty(vin)) {
                    mVinNew.setText(vin);
                } else {
                    if (isCar) {
                        finish();
                    }
                }
            } else {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    dialog.cancel();
                }
                if (isCar) {
                    finish();
                }
            }
        }
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
            vinVerfiyNewCar.setVisibility(View.GONE);
            String vin = mVinNew.getText().toString();
            if (vin.length() == 17) {
                if (VINutils.checkVIN(vin)) {
                    dialog.show();
                    dialog.setLoadingTitle("正在查询车辆...");
                    vinVerfiyNewCar.setVisibility(View.GONE);
                    getVinMoble(vin);
                } else {
                    vinVerfiyNewCar.setVisibility(View.VISIBLE);
                }
            }
            if (vin.length() > 17) {
                vin = vin.substring(0, 16);
                mVinNew.setText(vin);
                mVinNew.setSelection(vin.length());
            }
        }
    };
    private TextWatcher priceWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String price = priceBuyNewCar.getText().toString();
            if (GeneralUtils.isEmpty(price))
                price = "0";
            try {
                double priceNum = Integer.parseInt(price);
                if (priceNum != 0 && priceNum > 10000) {
                    caleBuyLl.setVisibility(View.VISIBLE);
                    double num = priceNum / 10000.00;
                    DecimalFormat df = new DecimalFormat("######0.00");
                    calePriceBuyNewCar.setText("实际购入价：" + df.format(num) + " 万元");
                } else {
                    caleBuyLl.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /* 根据vin查询车辆 */
    private void getVinMoble(final String vinCode) {
        dialog.setLoadingTitle("正在查询车辆...");
        dialog.show();
        isCarExit=true;
        HttpBank.getIntence(this).getVinBrand(vinCode, new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug("根据vin查询车辆 = " + o.toString());
                QueryVinBean bean = QueryVinBean.getQueryVinBean(o.toString());
                if (bean != null && bean.isSuccess()) {
                    if ("YES".equals(bean.getState())) {
                        showExit(vinCode, bean);
                    } else {
                        carValues(bean);
                    }
                } else {
                    showVINDialog();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                showVINDialog();
            }
        });
    }

    boolean isCarExit = false;

    /* 判断该VIN是否已经添加 */
    private void showExit(String strs, final QueryVinBean bean) {
        DialogUtil.prompt(this, "你是否再次添加该车辆？" + strs, "取消", "添加", new DialogButtonListener() {
            @Override
            public void ok() {
                carValues(bean);
                isCarExit = true;
            }

            @Override
            public void cancel() {
                isCarExit = false;
            }
        });
    }

    private void carValues(QueryVinBean bean) {
        if (bean.getPrice() != 0) {
            priceLlNewCar.setVisibility(View.VISIBLE);
            priceNewCar.setText("厂家新车标准售价：" + bean.getPrice() + " 万");
            priceBuyNewCar.setText((int) (bean.getPrice() * 10000) + "");
            if (bean.getPrice() > 1) {
                calePriceBuyNewCar.setText("实际购入价：" + bean.getPrice() + " 万元");
            }
        } else {
            priceLlNewCar.setVisibility(View.GONE);
        }
        if (!GeneralUtils.isEmpty(bean.getBrand())) {
            mBrandNewCar.setText(bean.getBrand());
            mSubBrandNewCar.setText(bean.getSubBrand());
            mModuleNewCar.setText(bean.getModel());
        } else {
            showVINDialog();
        }
    }

    /* 未查询到车辆信息提示 */
    private void showVINDialog() {
        DialogUtil.prompt(NewCarActivity.this, "未能根据VIN获取到车型信息,请重新输入VIN或手动输入", "继续", new DialogButtonListener() {
            @Override
            public void ok() {
                mBrandNewCar.setText("");
                mSubBrandNewCar.setText("");
                mModuleNewCar.setText("");
            }

            @Override
            public void cancel() {

            }
        });
    }

    /* 获取车型数据 */
    private void getBrand() {
        if (status == 0) {
            subid = "";
            modles = "";
        } else if (status == 1) {
            subid = sbid;
            modles = "";
        } else {
            subid = sbid;
            modles = mid;
        }
        boolean isBrand = (boolean) get(this, "isBrand", false);
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
                items.clear();
                if (status != 0) {
                    BrandBean.BrandItems item = new BrandBean.BrandItems();
                    if (status == 1)
                        item.setSubBrand("上一级");
                    else
                        item.setModel("上一级");
                    items.add(item);
                }
                items.addAll(list);
                mAdapter.notifyDataSetChanged();
                mAdapter.setTitles(null);
                if (mDialg == null || !mDialg.isShowing()) {
                    showModelBrand();
                }
                if (items.size() == 0) {
                    spinnerList.setVisibility(View.GONE);
                    spinnerNoData.setVisibility(View.VISIBLE);
                    mDialg.setCanceledOnTouchOutside(true);
                } else {
                    spinnerList.setVisibility(View.VISIBLE);
                    spinnerNoData.setVisibility(View.GONE);
                }
                return;
            }
        }
        HttpBank.getIntence(this).httpCat("", subid, modles, new CallBack<Object>() {
            @Override
            public void onSuccess(Object o) {
                LogUtils.debug("获取品牌:" + o.toString());
                BrandBean brand = BrandBean.getBrandBean(o.toString());
                if (brand != null && brand.isSuccess() && brand.getItems() != null) {
                    if (brand.getItems().size() > 0)
                        IBaseMethod.saveBrand(NewCarActivity.this, db, brand.getItems(), subid, modles);
                    items.clear();
                    if (status != 0) {
                        BrandBean.BrandItems item = new BrandBean.BrandItems();
                        if (status == 1)
                            item.setSubBrand("上一级");
                        else
                            item.setModel("上一级");
                        items.add(item);
                    }
                    items.addAll(brand.getItems());
                    mAdapter.notifyDataSetChanged();
                    mAdapter.setTitles(null);
                    if (mDialg == null || !mDialg.isShowing()) {
                        showModelBrand();
                    }
                    if (items.size() == 0) {
                        spinnerList.setVisibility(View.GONE);
                        spinnerNoData.setVisibility(View.VISIBLE);
                        mDialg.setCanceledOnTouchOutside(true);
                    } else {
                        spinnerList.setVisibility(View.VISIBLE);
                        spinnerNoData.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {

            }
        });
    }

    Dialog mDialg;
    ListView spinnerList;
    TextView spinnerNoData;

    private void showModelBrand() {
        mDialg = new Dialog(this, R.style.dialogFullHeight);
        View view = View.inflate(this, com.kaopujinfu.appsys.customlayoutlibrary.R.layout.dialog_spinner_search, null);
        final EditText spinnerKey = (EditText) view.findViewById(com.kaopujinfu.appsys.customlayoutlibrary.R.id.spinnerKey);
        spinnerList = (ListView) view.findViewById(com.kaopujinfu.appsys.customlayoutlibrary.R.id.spinnerList);
        spinnerNoData = (TextView) view.findViewById(com.kaopujinfu.appsys.customlayoutlibrary.R.id.spinnerNoData);
        if (mAdapter != null && mAdapter.getCount() > 0) {
            spinnerNoData.setVisibility(View.GONE);
            spinnerList.setVisibility(View.VISIBLE);
            spinnerList.setAdapter(mAdapter);
            spinnerKey.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    final List<String> titles = mAdapter.getTitles();
                    String searchText = spinnerKey.getText().toString();
                    if (searchText.length() > 0 && titles != null && titles.size() > 0) {
                        List<String> tempTitles = new ArrayList<>();
                        if (!searchText.contains("上") && !searchText.contains("一") && !searchText.contains("级")) {
                            tempTitles.add("上一级");
                        }
                        for (String title : titles) {
                            if (title != null && title.contains(searchText)) {
                                tempTitles.add(title);
                            }
                        }
                        mAdapter.setTitles(tempTitles);
                    } else if (searchText.length() <= 0) {
                        mAdapter.setTitles(null);
                    }
                }
            });
            spinnerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    BrandBean.BrandItems brandItems = mAdapter.getListsBean(i);
                    spinnerKey.setText("");
                    if (status == 0) {
                        status = 1;
                        sbid = brandItems.getBrandId();
                        mid = "";
                        mBrandNewCar.setText(brandItems.getBrand());
                        mSubBrandNewCar.setText("");
                        mModuleNewCar.setText("");
                        getBrand();
                    } else if (status == 1) {
                        if (i == 0) {
                            LogUtils.debug("进入....");
                            status = 0;
                            getBrand();
                            return;
                        }
                        status = 2;
                        mid = brandItems.getSubBrandId();
                        mSubBrandNewCar.setText(brandItems.getSubBrand());
                        getBrand();
                    } else {
                        if (i == 0) {
                            status = 1;
                            getBrand();
                            return;
                        }
                        mModuleNewCar.setText(brandItems.getModel());
                        mDialg.dismiss();
                    }

                }
            });
        } else {
            spinnerList.setVisibility(View.GONE);
            spinnerNoData.setVisibility(View.VISIBLE);
        }
        mDialg.setContentView(view);
        WindowManager m = this.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = mDialg.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        mDialg.getWindow().setAttributes(p);
        mDialg.getWindow().setGravity(Gravity.CENTER);
        mDialg.setCanceledOnTouchOutside(false);
        mDialg.setCancelable(true);
        mDialg.show();
    }

    /* 显示时间对话框 */
    private void showDate() {
        String startDate = DateUtil.getSimpleDateYYYYMMDDHHMMM(System.currentTimeMillis());
        Calendar c = Calendar.getInstance(); //当前时间
        c.add(Calendar.YEAR, 100);
        String endDate = DateUtil.getSimpleDateYYYYMMDDHHMMM(c.getTimeInMillis());
        TimeSelector timeSelector = new TimeSelector(this, new TimeSelector.ResultHandler() {
            @Override
            public void handle(String time) {
                String juli = DateUtil.howMach(time);
                caleDateNewCar.setText("距离现在: " + juli);
                goDateNewCar.setText(time.substring(0, time.indexOf(" ")));
            }
        }, "1900-01-01 00:00:00", startDate, startDate);
        timeSelector.setMode(TimeSelector.MODE.YMD);//只显示 年月日
        timeSelector.setIsLoop(false);
        timeSelector.show();
    }

    /* 提交 */
    private void commitNewCar() {
        if (gpsEntity == null || GeneralUtils.isEmpty(gpsEntity.getDlr())) {
            IBaseMethod.showToast(this, "请选择经销商", IBase.RETAIL_TWO);
            return;
        }
        final String vincode = mVinNew.getText().toString();
        if (!VINutils.checkVIN(vincode)) {
            vinVerfiyNewCar.setVisibility(View.VISIBLE);
            return;
        }
        String brand = mBrandNewCar.getText().toString();
        String subBrand = mSubBrandNewCar.getText().toString();
        String model = mModuleNewCar.getText().toString();
        String price = priceBuyNewCar.getText().toString();
        String mileage = mileageNewCar.getText().toString();
        String date = goDateNewCar.getText().toString();
        if (GeneralUtils.isEmpty(price)) {
            IBaseMethod.showToast(this, "请输入车辆的价格", IBase.RETAIL_TWO);
            return;
        }
        String colorStr = colorCar_new.getText().toString();
        String licenseplatenumber = licenseplatenumberCar.getText().toString();
//        if (GeneralUtils.isEmpty(licenseplatenumber)) {
//            IBaseMethod.showToast(this, "请输入车辆的车牌号", IBase.RETAIL_TWO);
//            return;
//        }
        String parkinglot = parkinglotCar.getText().toString();
//        if (GeneralUtils.isEmpty(parkinglot)) {
//            IBaseMethod.showToast(this, "请输入车位", IBase.RETAIL_TWO);
//            return;
//        }
        String isTwo = "";
        if (isTwoCar.isChecked()) {
            isTwo = "on";
            if (GeneralUtils.isEmpty(mileage)) {
                IBaseMethod.showToast(this, "二手车必须填写车辆行驶的里程", IBase.RETAIL_TWO);
                return;
            }
            if (GeneralUtils.isEmpty(date)) {
                IBaseMethod.showToast(this, "二手车必须填写车辆上牌时间", IBase.RETAIL_TWO);
                return;
            }
        }
        dialog.show();
        dialog.setLoadingTitle("正在提交...");
        HttpBank.getIntence(this).commiNewCar(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                dialog.dismiss();
                LogUtils.debug("新增车辆：" + o.toString());
                Result result = Result.getMcJson(o.toString());
                if (result != null && result.isSuccess()) {
                    SPUtils.put(NewCarActivity.this, IBase.USERID + "vinCode", vincode);
                    IBaseMethod.showToast(NewCarActivity.this, "提交成功", IBase.RETAIL_ONE);

                    //通知首页统计数据发改变
                    JumpEventBus jumpEventBus = new JumpEventBus();
                    jumpEventBus.setStatus(IBase.RETAIL_THREE);
                    EventBus.getDefault().post(jumpEventBus);
//
                    if (isCar) {
                        RetailAplication.getInstance().exitAllActicity();
                    }
                    Intent intent = new Intent(NewCarActivity.this, DocumentCommitActivity.class);
                    intent.putExtra("success", IBase.CONSTANT_THREE);
                    startActivity(intent);
                    finish();
                } else {
                    IBaseMethod.showToast(NewCarActivity.this, result.getComment(), IBase.RETAIL_ZERO);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                dialog.dismiss();
                if (errorNo == IBase.CONSTANT_ONE) {
                    IBaseMethod.showNetToast(NewCarActivity.this);
                }
            }
        }, gpsEntity.getDlr(), vincode, isTwo, brand, subBrand, model, price, mileage, date, colorStr, licenseplatenumber, parkinglot);
    }

}
