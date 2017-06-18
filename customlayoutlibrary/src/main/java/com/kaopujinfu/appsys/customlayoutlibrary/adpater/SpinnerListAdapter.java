package com.kaopujinfu.appsys.customlayoutlibrary.adpater;

import android.widget.ListAdapter;

import java.util.List;

/**
 * 下拉列表对话框
 * Created by Doris on 2017/5/23.
 */
public interface SpinnerListAdapter extends ListAdapter{

    /**
     * 获取列表
     * @return
     */
    List<String> getTitles();

    /**
     * 设置列表
     * @return
     */
    void setTitles(List<String> titles);

}
