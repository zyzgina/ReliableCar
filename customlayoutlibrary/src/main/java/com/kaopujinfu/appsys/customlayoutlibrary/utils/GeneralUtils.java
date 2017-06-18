package com.kaopujinfu.appsys.customlayoutlibrary.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by t00284576 on 2015/10/27.
 */
public class GeneralUtils {
    /**
     * 判断是否是正确的手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if (isEmpty(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();

    }

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            if ("".equals(((String) object).trim()) || "null".equals((String) object)) {
                return true;
            } else {
                return false;
            }
        }

        if (object instanceof List) {
            return ((List) object).isEmpty();
        }

        return false;

    }


    /**
     * 判断对象是否为空
     *
     * @param o
     * @return
     */
    public static boolean isNull(Object o) {
        return o == null ? true : false;
    }

    /**
     * parameter 2 is contain in parameter 1.
     *
     * @param sourceFlag
     * @param compareFlag
     * @return
     */
    public static boolean isFlagContain(int sourceFlag, int compareFlag) {
        return (sourceFlag & compareFlag) == compareFlag;
    }
}
