package com.kaopujinfu.appsys.customlayoutlibrary.eventbus;

/**
 * 页面跳转广播
 * Created by 左丽姬 on 2017/4/5.
 */

public class JumpEventBus {
    private int status = 0;//0、获取个人信息；1、上传完成；2、首页-》产品推荐 ; 3、侧滑菜单
    //status=0x100 网络重新加载发送的广播 status=0x103 盘库成功数据发生改变，文档绑定成功数据发生改变，监管器绑定成功数据发生改变
    //status=0x104 接口超过响应时间
    private String name;//name="com.reliablel.retail.service.STOPSERVICE";  关闭服务

    public JumpEventBus() {
    }

    public JumpEventBus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
