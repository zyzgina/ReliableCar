package com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle;

public class EngineConfig {
    public int mVideoProfile;

    public int mUid;

    public String mChannel;

    public String channelKey;

    public String appId;

    public void reset() {
        mChannel = null;
        channelKey=null;
    }

    EngineConfig() {
    }
}
