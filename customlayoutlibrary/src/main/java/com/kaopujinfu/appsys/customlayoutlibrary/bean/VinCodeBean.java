package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;

/**
 * Describe: vin码保存
 * Created Author: Gina
 * Created Date: 2017/6/29.
 */
@Table(name = "vinBean")
public class VinCodeBean implements Serializable {
    @Id(column = "id")
    private int id;
    private String vinCode;
    private String savetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getSavetime() {
        return savetime;
    }

    public void setSavetime(String savetime) {
        this.savetime = savetime;
    }

    @Override
    public String toString() {
        return "VinCodeBean{" +
                "id=" + id +
                ", vinCode='" + vinCode + '\'' +
                ", savetime='" + savetime + '\'' +
                '}';
    }
}
