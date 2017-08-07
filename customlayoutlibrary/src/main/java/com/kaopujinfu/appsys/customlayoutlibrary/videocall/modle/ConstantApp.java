package com.kaopujinfu.appsys.customlayoutlibrary.videocall.modle;

import io.agora.rtc.Constants;

public class ConstantApp {
    public static final int MAX_PEER_COUNT = 4;
    public static int[] VIDEO_PROFILES = new int[]{Constants.VIDEO_PROFILE_120P,
            Constants.VIDEO_PROFILE_180P,
            Constants.VIDEO_PROFILE_240P,
            Constants.VIDEO_PROFILE_360P,
            Constants.VIDEO_PROFILE_480P,
            Constants.VIDEO_PROFILE_720P};

    public static final int DEFAULT_PROFILE_IDX = 2; // default use 240P

    public static class PrefManager {
        public static final String PREF_PROPERTY_PROFILE_IDX = "pref_profile_index";
        public static final String PREF_PROPERTY_UID = "pOCXx_uid";
    }
    public static final String ACTION_KEY_CHANNEL_NAME = "channelName";
    public static final String ACTION_KEY_CHANNEL_UID = "channelUid";
    public static final String ACTION_KEY_CHANNEL_KEY = "channelKey";
    //启用内置加密功能
    public static final String ACTION_KEY_ENCRYPTION_KEY = "xdL_encr_key_";
    //设置内置的加密方案
    public static final String ACTION_KEY_ENCRYPTION_MODE = "tOK_edsx_Mode";

    public static class AppError {
        public static final int NO_NETWORK_CONNECTION = 3;
    }
}
