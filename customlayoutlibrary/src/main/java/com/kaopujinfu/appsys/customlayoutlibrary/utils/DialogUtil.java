package com.kaopujinfu.appsys.customlayoutlibrary.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kaopujinfu.appsys.customlayoutlibrary.R;
import com.kaopujinfu.appsys.customlayoutlibrary.RetailAplication;
import com.kaopujinfu.appsys.customlayoutlibrary.adpater.ReleaseDialogAdapter;
import com.kaopujinfu.appsys.customlayoutlibrary.adpater.SpinnerListAdapter;
import com.kaopujinfu.appsys.customlayoutlibrary.bean.ReleaseIcno;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogButtonListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogCameraListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogItemListener;
import com.kaopujinfu.appsys.customlayoutlibrary.listener.DialogReleaseListener;
import com.kaopujinfu.appsys.customlayoutlibrary.tools.IBase;
import com.kaopujinfu.appsys.customlayoutlibrary.view.ErrorView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.MyGridView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.TickView;
import com.kaopujinfu.appsys.customlayoutlibrary.view.WarningView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 对话框
 * Created by Doris on 2017/4/24.
 */

public class DialogUtil {

    private static Dialog dialog;

    /**
     * 提示对话框
     *
     * @param context
     * @param info     提示信息
     * @param listener 确定/取消事件
     */
    public static void prompt(Context context, String info, DialogButtonListener listener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context, R.style.dialogFullHeight);
        dialog.setContentView(getPromptView(context, listener, info));
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.95
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 提示对话框
     *
     * @param context
     * @param info     提示信息
     * @param okInfo   确定按钮显示信息
     * @param listener 确定/取消事件
     */
    public static void prompt(Context context, String info, String okInfo, DialogButtonListener listener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context, R.style.dialogFullHeight);
        dialog.setContentView(getPromptView(context, listener, info, okInfo));
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.95
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * 提示对话框
     *
     * @param context
     * @param info       提示信息
     * @param cancelInfo 取消按钮显示信息
     * @param okInfo     确定按钮显示信息
     * @param listener   确定/取消事件
     */
    public static void prompt(Context context, String info, String cancelInfo, String okInfo, DialogButtonListener listener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context, R.style.dialogFullHeight);
        dialog.setContentView(getPromptView(context, listener, info, cancelInfo, okInfo));
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.95
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    /**
     * @param strings
     */
    private static View getPromptView(Context context, final DialogButtonListener listener, String... strings) {
        View view = View.inflate(context, R.layout.dialog_prompt, null);
        TextView tv_info_prompt = (TextView) view.findViewById(R.id.tv_info_prompt);
        tv_info_prompt.setText(strings[0]);
        TextView b_cancel_prompt = (TextView) view.findViewById(R.id.b_cancel_prompt);
        TextView b_ok_prompt = (TextView) view.findViewById(R.id.b_ok_prompt);
        b_ok_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.ok();
                }
                dialog.dismiss();
            }
        });
        if (strings.length == 2) {
            b_ok_prompt.setText(strings[1]);
            b_cancel_prompt.setVisibility(View.GONE);
        } else if (strings.length == 3) {
            b_cancel_prompt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.cancel();
                    }
                    dialog.dismiss();
                }
            });
            b_cancel_prompt.setText(strings[1]);
            b_ok_prompt.setText(strings[2]);
        }
        return view;
    }

    /**
     * 获取验证码
     */
    public static void verificationCode(final Context context, String[] listMenu, final DialogItemListener listener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        View view = View.inflate(context, R.layout.dialog_verification_code, null);
        ListView lv_dialog = (ListView) view.findViewById(R.id.lv_dialog);
        ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_dialog, R.id.tv_item_dialog, listMenu);
        lv_dialog.setAdapter(arrayAdapter);
        lv_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (listener != null) {
                    listener.itemListener(i);
                }
                dialog.dismiss();
            }
        });

        dialog = new Dialog(context, R.style.dialogFullHeight);
        dialog.setContentView(view);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.95
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }



    /**
     * 选择图片
     *
     * @param context
     * @param fileName
     * @param listener
     */
    public static void selectPicDialog(final Context context, final String fileName, final DialogCameraListener listener) {
        selectPicDialog(context,fileName,0,listener);
    }

    public static void selectPicDialog(final Context context, final String fileName,int status, final DialogCameraListener listener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_pic, null);
        Button avatar_phone = (Button) view.findViewById(R.id.avatar_phone);
        Button avatar_image = (Button) view.findViewById(R.id.avatar_image);
        Button avatar_cancel = (Button) view.findViewById(R.id.avatar_cancel);
        if(status==1){
            avatar_phone.setVisibility(View.GONE);
            avatar_image.setBackgroundResource(R.drawable.shape_dialog_below_4);
        }
        View.OnClickListener mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.avatar_phone) {
                    // 拍照
                    if (listener != null) {
                        if (listener.takePhoto()) {
                            takePhoto((Activity) context, fileName);
                        }
                    } else {
                        takePhoto((Activity) context, fileName);
                    }
                    dialog.dismiss();
                } else if (v.getId() == R.id.avatar_image) {
                    // 从相册中选择
                    if (listener != null) {
                        if (listener.selectImage()) {
                            selectPhoto((Activity) context);
                        }
                    } else {
                        selectPhoto((Activity) context);
                    }
                    dialog.dismiss();
                } else if (v.getId() == R.id.avatar_cancel) {
                    // 取消
                    dialog.dismiss();
                }
            }
        };
        avatar_phone.setOnClickListener(mListener);
        avatar_image.setOnClickListener(mListener);
        avatar_cancel.setOnClickListener(mListener);

        dialog = new Dialog(context, R.style.PassDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * 选择发布信息
     */
    public static void selectReleaseDialog(final Context context, final List<ReleaseIcno> icnos, final DialogReleaseListener listener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_release, null);
        Button release_cancel = (Button) view.findViewById(R.id.release_cancel);
        MyGridView myGridView = (MyGridView) view.findViewById(R.id.dialog_release);
        ReleaseDialogAdapter adapter = new ReleaseDialogAdapter(context, icnos);
        myGridView.setAdapter(adapter);
        View.OnClickListener mListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.release_cancel) {
                    // 取消
                    dialog.dismiss();
                }
            }
        };
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.okClick(position);
                    dialog.dismiss();
                }
            }
        });
        release_cancel.setOnClickListener(mListener);


        dialog = new Dialog(context, R.style.PassDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    private static void takePhoto(Activity activity, String fileName) {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), fileName)));
        activity.startActivityForResult(takeIntent, IBase.RETAIL_ONE);
    }

    private static void selectPhoto(Activity activity) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果朋友们要限制上传到服务器的图片类型时可以直接写如：image/jpeg 、 image/png等的类型
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(pickIntent, IBase.RETAIL_ZERO);
    }

    /**
     * 下拉列表对话框
     *
     * @param context
     * @param adapter
     */
    public static void spinnerDilaog(final Context context, SpinnerListAdapter adapter, final DialogItemListener listener) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        View view = View.inflate(context, R.layout.dialog_spinner_search, null);
        final EditText spinnerKey = (EditText) view.findViewById(R.id.spinnerKey);
        final ListView spinnerList = (ListView) view.findViewById(R.id.spinnerList);
        final TextView spinnerNoData = (TextView) view.findViewById(R.id.spinnerNoData);
        final SpinnerListAdapter mAdapter = adapter;
        if (mAdapter != null && mAdapter.getCount() > 0) {
            final List<String> titles = mAdapter.getTitles();
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
                    String searchText = spinnerKey.getText().toString();
                    if (searchText.length() > 0 && titles != null && titles.size() > 0) {
                        List<String> tempTitles = new ArrayList<>();
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
                    if (listener != null) {
                        listener.itemListener(i);
                    }
                    dialog.dismiss();
                }
            });
        } else {
            spinnerList.setVisibility(View.GONE);
            spinnerNoData.setVisibility(View.VISIBLE);
        }
        dialog = new Dialog(context, R.style.dialogFullHeight);
        dialog.setContentView(view);
        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        dialog.getWindow().setAttributes(p);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * 库融IP地址修改对话框
     */
    public static void updateIPDialog(final Context context) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        String urlPath = SPUtils.get(RetailAplication.getContext(), "domain", "").toString();
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ipaddrss, null);
//        final EditText logIp = (EditText) view.findViewById(R.id.logIp);
//        logIp.setText(IBaseUrl.URL);
        final EditText CarIp = (EditText) view.findViewById(R.id.CarIp);
        if (!GeneralUtils.isEmpty(urlPath))
            CarIp.setText(urlPath);
        Button commitIp = (Button) view.findViewById(R.id.commitIp);
        commitIp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String car = CarIp.getText().toString();
                dialog.dismiss();
                dialog.cancel();
                SPUtils.put(RetailAplication.getContext(), "domain", car);
                LogUtils.debug("输入:" + car);
            }
        });

        dialog = new Dialog(context);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
    }

    /**
     * 正确错误动态提示框 -是否触摸消失
     *
     * @param context
     * @param title   提示的文字
     * @param butText Button 的文字
     * @param status  0 错误提示  1 正确提示
     */
    public static void jumpCorrectErr(Context context, String title, String butText, int status) {
        jumpCorrectErr(context, title, butText, status, context.getResources().getColor(android.R.color.holo_green_light));
    }

    /**
     * 正确错误动态提示框 -是否触摸消失
     *
     * @param context
     * @param title   提示的文字
     * @param butText Button 的文字
     * @param status  0 错误提示  1 正确提示
     * @param bgColor 是否触摸消失
     */
    public static void jumpCorrectErr(Context context, String title, String butText, int status, int bgColor) {
        jumpCorrectErr(context, title, butText, status, bgColor, true);
    }


    /**
     * 正确错误动态提示框 -是否触摸消失
     *
     * @param context
     * @param title    提示的文字
     * @param butText  Button 的文字
     * @param status   0 错误提示  1 正确提示
     * @param bgColor  button的背景颜色
     * @param isCanlce 是否触摸消失
     */
    public static void jumpCorrectErr(Context context, String title, String butText, int status, int bgColor, boolean isCanlce) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new Dialog(context);
        View view = View.inflate(context, R.layout.dialog_toats, null);
        dialog.show();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(isCanlce);
        TextView mTitle = (TextView) view.findViewById(R.id.title_load);
        mTitle.setText(title);
        TickView mTick = (TickView) view.findViewById(R.id.tickView);
        ErrorView mError = (ErrorView) view.findViewById(R.id.errorView);
        WarningView mWarning= (WarningView) view.findViewById(R.id.warningView);
        Button continueDialog = (Button) view.findViewById(R.id.continueDialog);
        if (!GeneralUtils.isEmpty(butText))
            continueDialog.setText(butText);
        continueDialog.setVisibility(View.VISIBLE);
        if (status == 0) {
            mError.setVisibility(View.VISIBLE);
        }
        if (status == 1) {
            mTick.setVisibility(View.VISIBLE);
        }
        if(status == 2){
            mWarning.setVisibility(View.VISIBLE);
        }
        if (bgColor != 0) {
            continueDialog.setBackgroundColor(bgColor);
        }
        continueDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dialog.cancel();
            }
        });

        WindowManager m = ((Activity) context).getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (d.getWidth() * 0.9); // 宽度设置为屏幕的0.9
        p.height = (int) (d.getHeight() * 0.3);
        dialog.getWindow().setAttributes(p);
    }

}
