package com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture;

/**
 * Created by Doris on 2017/5/18.
 */

public class PrepareCameraException extends Exception {

    private static final String MESSAGE = "Unable to use camera for recording";

    private static final long serialVersionUID = 6305923762266448674L;

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
