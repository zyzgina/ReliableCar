package com.kaopujinfu.appsys.thecar.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * 上传文件
 * Created by Doris on 2017/5/17.
 */
@Table(name = "tb_UploadFile") // 表名
public class UploadFile {

    @Id(column = "fileId")  // 设置主键列名
    private String fileId;// 数据库主键
    // 文件短名称：
    // 规则：[业务类型名]_[(短)年月日时分秒]_[4位随机值].[文件类型]
    // 例子：车辆外观_170515164109_1343.JPG
    private String fileName;
    // 存储在七牛云上的KEY：
    // 规则：[业务类型代号]/[年月日]/[时分秒]_[6字符随机码].[文件类型]
    // 例子：BIND_DEV/20170515/164706_182719.JPG
    private String storeKey;
    // 业务类型名称，比如：监管器绑定、文档绑定、车辆外观
    private String bizType;
    // 业务编号，比如一个任务号，或者一部车辆的VIN码等
    private String bizId;
    // 文件路径
    private String filePath;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
