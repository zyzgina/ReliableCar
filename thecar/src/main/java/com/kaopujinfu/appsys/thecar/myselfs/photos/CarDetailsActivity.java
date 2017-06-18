package com.kaopujinfu.appsys.thecar.myselfs.photos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.activitys.BaseNoScoActivity;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyListView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyRatingBar;
import com.kaopujinfu.appsys.customlayoutlibrary.view.ObserveScrollView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.TagLayout;
import com.kaopujinfu.appsys.thecar.R;
import com.kaopujinfu.appsys.thecar.adapters.CarLabelAdapter;
import com.kaopujinfu.appsys.thecar.adapters.CarParameterConfigAdapter;
import com.kaopujinfu.appsys.thecar.adapters.CarRecommendAdapter;
import com.kaopujinfu.appsys.thecar.adapters.CarTestAdapter;
import com.kaopujinfu.appsys.thecar.adapters.ConfigStrengthsAdapter;
import com.squareup.picasso.Picasso;
import com.test.arrowposition.view.PricesAssessment;

/**
 * 车辆详情
 * Created by Doris on 2017/5/24.
 */
public class CarDetailsActivity extends BaseNoScoActivity implements View.OnClickListener{

    public static String imageUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494324737128&" +
            "di=4d8cd7525da712842b160ce6d4fe9f43&imgtype=0&src=http%3A%2F%2Fwww.rmzt.com%2Fuploads%2Fallimg%2F160324%" +
            "2F1-160324162H20-L.jpg";
    private ImageView carDetailsImage;
    // 评价
    private MyRatingBar carDetailsEvaluate;
    private TagLayout carDetailsLabel;
    private CarLabelAdapter carLabelAdapter;
    // 参数配置
    private MyGridView carDetailsParameterConfig;
    private CarParameterConfigAdapter carParameterConfigAdapter;
    // 配置亮点
    private MyGridView carDetailsConfigStrengths;
    private ConfigStrengthsAdapter configStrengthsAdapter;
    // 好车推荐
    private MyListView carDetailsRecommend;
    private CarRecommendAdapter carRecommendAdapter;
    // 质量检测
    private MyGridView carDetailsTestBusiness;
    private CarTestAdapter carTestAdapter;
    // 价格评估
    private PricesAssessment carDetailsPricesAssessmentProgress;
    // 经销商地址
    private TextView carDetailsDistributorAddress;
    private ObserveScrollView scrollviewCarDetails;
    private ImageView rollCarDetasil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);
        IBaseMethod.setBarStyle(this, getResources().getColor(R.color.car_theme));
    }

    @Override
    public void initView() {
        dialog.dismiss();
        mTvTitle.setText("车辆详情");
        header.setBackgroundColor(getResources().getColor(R.color.car_theme));
        header.setPadding(0, IBaseMethod.setPaing(this), 0, 0);
        top_btn.setVisibility(View.GONE);
        top_meun.setVisibility(View.VISIBLE);
        top_meun.setImageResource(R.drawable.preview_icon_share);

        scrollviewCarDetails = (ObserveScrollView) findViewById(R.id.scrollviewCarDetails);
        scrollviewCarDetails.setScrollListener(scrollChangeListener);
        rollCarDetasil = (ImageView) findViewById(R.id.rollCarDetasil);
        rollCarDetasil.setOnClickListener(this);
        // 车辆图片
        carDetailsImage = (ImageView) findViewById(R.id.carDetailsImage);
        Picasso.with(this).load(imageUrl).into(carDetailsImage);
        // 星级评分(不可点击)
        carDetailsEvaluate = (MyRatingBar) findViewById(R.id.carDetailsEvaluate);
        carDetailsEvaluate.setClickable(false);
        // 评价
        carDetailsLabel = (TagLayout) findViewById(R.id.carDetailsLabel);
        carLabelAdapter = new CarLabelAdapter(this);
        carDetailsLabel.setAdapter(carLabelAdapter);
        // 参数配置
        carDetailsParameterConfig = (MyGridView) findViewById(R.id.carDetailsParameterConfig);
        carParameterConfigAdapter = new CarParameterConfigAdapter(this);
        carDetailsParameterConfig.setAdapter(carParameterConfigAdapter);
        // 配置亮点
        carDetailsConfigStrengths = (MyGridView) findViewById(R.id.carDetailsConfigStrengths);
        configStrengthsAdapter = new ConfigStrengthsAdapter(this);
        carDetailsConfigStrengths.setAdapter(configStrengthsAdapter);
        // 质量检测
        carDetailsTestBusiness = (MyGridView) findViewById(R.id.carDetailsTestBusiness);
        carTestAdapter = new CarTestAdapter(this);
        carDetailsTestBusiness.setAdapter(carTestAdapter);
        // 好车推荐
        carDetailsRecommend = (MyListView) findViewById(R.id.carDetailsRecommend);
        carRecommendAdapter = new CarRecommendAdapter(this);
        carDetailsRecommend.setAdapter(carRecommendAdapter);
        // 价格评估
        carDetailsPricesAssessmentProgress = (PricesAssessment) findViewById(R.id.carDetailsPricesAssessmentProgress);
        carDetailsPricesAssessmentProgress.setProgress(0.3f, 0.4f, 0.3f,0.5f);
        carDetailsPricesAssessmentProgress.setProgressDownText("3.00万");
        carDetailsPricesAssessmentProgress.setProgressNormalText("当前报价5.00万");
        carDetailsPricesAssessmentProgress.setProgressUpText("3.00万");
        // 经销商地址
        carDetailsDistributorAddress = (TextView) findViewById(R.id.carDetailsDistributorAddress);
        carDetailsDistributorAddress.setOnClickListener(this);
    }

    /**
     * 360度看车
     * @param view
     */
    public void checkCar360(View view){
        Intent intent = new Intent(this, CarCheckActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view == carDetailsDistributorAddress){
            // 经销商地址
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("longitude", 31.207494); // 经度
            intent.putExtra("latitude", 121.444693); // 纬度
            intent.putExtra("address", "上海图书馆"); // 地址
            startActivity(intent);
        }else if(view==rollCarDetasil){
            scrollviewCarDetails.fullScroll(View.FOCUS_UP);
        }
    }

    private ObserveScrollView.ScrollListener scrollChangeListener=new ObserveScrollView.ScrollListener() {
        @Override
        public void scrollOritention(int l, int t, int oldl, int oldt) {
            if(t>500){
                rollCarDetasil.setVisibility(View.VISIBLE);
            }else{
                rollCarDetasil.setVisibility(View.GONE);
            }
        }
    };
}
