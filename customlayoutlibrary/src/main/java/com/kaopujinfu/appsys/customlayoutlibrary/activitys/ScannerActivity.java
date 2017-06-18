package com.kaopujinfu.appsys.customlayoutlibrary.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;
import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBaseMethod;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

/**
 * 条形和二维码扫描
 * Created by Doris on 2017/5/15.
 */
public class ScannerActivity extends Activity {

    private ScannerView scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scanner = (ScannerView) findViewById(R.id.scanner);
        // 扫描框4角颜色
//        scanner.setLaserFrameBoundColor(getResources().getColor(R.color.car_theme));
        // 扫描线颜色
//        scanner.setLaserColor(getResources().getColor(R.color.car_theme));
        scanner.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void OnScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                ParsedResultType type = parsedResult.getType();
                switch (type){
                    case TEXT:
                        Intent intent = new Intent();
                        intent.putExtra("result", rawResult.getText());
                        setResult(IBase.RETAIL_THREE, intent);
                        finish();
                        break;
                    default:
                        IBaseMethod.showToast(ScannerActivity.this, "此结果和输入内容不相符！", IBase.RETAIL_ZERO);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        scanner.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        scanner.onPause();
        super.onPause();
    }

}
