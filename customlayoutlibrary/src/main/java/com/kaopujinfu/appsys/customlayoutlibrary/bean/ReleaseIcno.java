package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import java.io.Serializable;

/**
 * Created by 左丽姬 on 2017/5/24.
 */

public class ReleaseIcno implements Serializable {
    private String name;
    private int icon;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReleaseIcno{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                '}';
    }
}
