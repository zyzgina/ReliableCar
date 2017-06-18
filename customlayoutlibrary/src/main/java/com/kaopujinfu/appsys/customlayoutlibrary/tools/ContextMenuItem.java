package com.kaopujinfu.appsys.customlayoutlibrary.tools;

import android.graphics.drawable.Drawable;

/**
 * 上下文菜单项对象
 * Created by zuoliji on 2017/03/30.
 */
public class ContextMenuItem {

    private Drawable imgDrawable;
    private String title;
    private boolean visible;
    private int color;
    private int tag;

    public ContextMenuItem(Drawable imgDrawable, String title, boolean visible, int color) {
        this.imgDrawable = imgDrawable;
        this.title = title;
        this.visible = visible;
        this.color = color;
    }

    public ContextMenuItem(String title) {
        this.title = title;
    }

    public ContextMenuItem(Drawable imgDrawable, String title) {
        this.imgDrawable = imgDrawable;
        this.title = title;
    }

    public ContextMenuItem(Drawable imgDrawable, String title, int tag) {
        this.imgDrawable = imgDrawable;
        this.title = title;
        this.tag = tag;
    }

    public ContextMenuItem() {
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImgDrawable() {
        return imgDrawable;
    }

    public void setImgDrawable(Drawable imgDrawable) {
        this.imgDrawable = imgDrawable;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}

