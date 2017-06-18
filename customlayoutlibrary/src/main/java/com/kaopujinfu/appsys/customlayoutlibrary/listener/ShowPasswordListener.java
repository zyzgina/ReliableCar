package com.kaopujinfu.appsys.customlayoutlibrary.listener;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.CompoundButton;
import android.widget.EditText;

/**
 * Describe: 密码框改变显示喝隐藏
 * Created Author: Gina
 * Created Date: 2017/6/9.
 */

public class ShowPasswordListener implements CompoundButton.OnCheckedChangeListener{
    private EditText editText;

    public ShowPasswordListener(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            //如果选中，显示密码
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            //否则隐藏密码
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        // 光标移至最后
        editText.setSelection(editText.getText().length());
    }
}
