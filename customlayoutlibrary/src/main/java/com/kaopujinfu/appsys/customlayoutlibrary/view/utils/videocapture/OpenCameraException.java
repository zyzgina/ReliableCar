package com.kaopujinfu.appsys.customlayoutlibrary.view.utils.videocapture;

/**
 * Created by Doris on 2017/5/18.
 */

public class OpenCameraException extends Exception {

    private static final long	serialVersionUID	= -7340415176385044242L;

    public enum OpenType {
        INUSE("Camera disabled or in use by other process"), NOCAMERA("Device does not have camera");

        private String	mMessage;

        private OpenType(String msg) {
            mMessage = msg;
        }

        public String getMessage() {
            return mMessage;
        }

    }

    private final OpenType	mType;

    public OpenCameraException(OpenType type) {
        super(type.getMessage());
        mType = type;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }
}
