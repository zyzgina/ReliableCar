package com.kaopujinfu.appsys.customlayoutlibrary.view.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.kaopujinfu.appsys.customlayoutlibrary.R;

/**
 * 标签配置
 * Created by Doris on 2017/5/25.
 */
public class TagConfiguration {

    private static final int DEFAULT_LINE_SPACING = 5;
    private static final int DEFAULT_TAG_SPACING = 10;
    private static final int DEFAULT_FIXED_COLUMN_SIZE = 3; //默认列数

    private int lineSpacing;
    private int tagSpacing;
    private int columnSize;
    private boolean isFixed;

    public TagConfiguration(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagLayout);
        try {
            lineSpacing = a.getDimensionPixelSize(R.styleable.TagLayout_lineSpacing, DEFAULT_LINE_SPACING);
            tagSpacing = a.getDimensionPixelSize(R.styleable.TagLayout_tagSpacing, DEFAULT_TAG_SPACING);
            columnSize = a.getInteger(R.styleable.TagLayout_columnSize, DEFAULT_FIXED_COLUMN_SIZE);
            isFixed = a.getBoolean(R.styleable.TagLayout_isFixed, false);
        } finally {
            a.recycle();
        }
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public int getTagSpacing() {
        return tagSpacing;
    }

    public void setTagSpacing(int tagSpacing) {
        this.tagSpacing = tagSpacing;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }

}
