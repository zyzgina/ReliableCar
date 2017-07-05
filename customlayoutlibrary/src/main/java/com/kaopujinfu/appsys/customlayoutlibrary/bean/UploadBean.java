package com.kaopujinfu.appsys.customlayoutlibrary.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

import java.io.Serializable;

/**
 * Describe: 上传文件
 * Created Author: Gina
 * Created Date: 2017/6/13.
 */
@Table(name = "UploadBean")
public class UploadBean implements Serializable {
    @Id(column = "id")
    private int id;
    private String label;//保存是什么类型的图片;
    private String vinCode;
    private String loactionPath;//本地地址
    private String qny_key;//七牛云保存的key
    private String filename;//文件短名
    private String filesize;//文件大小
    private String userid;
    public int process=0;//进入条
    public String processText="0KB";//进入条文字显示

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getLoactionPath() {
        return loactionPath;
    }

    public void setLoactionPath(String loactionPath) {
        this.loactionPath = loactionPath;
    }

    public String getQny_key() {
        return qny_key;
    }

    public void setQny_key(String qny_key) {
        this.qny_key = qny_key;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "UploadBean{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", vinCode='" + vinCode + '\'' +
                ", loactionPath='" + loactionPath + '\'' +
                ", qny_key='" + qny_key + '\'' +
                ", filename='" + filename + '\'' +
                ", filesize='" + filesize + '\'' +
                ", userid='" + userid + '\'' +
                ", process=" + process +
                ", processText='" + processText + '\'' +
                '}';
    }
}
