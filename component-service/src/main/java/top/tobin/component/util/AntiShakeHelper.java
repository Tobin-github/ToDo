package top.tobin.component.util;

import android.util.Log;

/**
 * 防止暴力测试
 */
public class AntiShakeHelper {
    private static final String TAG = "AntiShakeHelper";

    private static final long INTERVAl = 500;

    private static long lastClickTimeStamp = 0;

    public static boolean enableClick(String msg) {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - lastClickTimeStamp) >= INTERVAl) {
            lastClickTimeStamp = currentTimeMillis;
            return true;
        } else {
            Log.d(TAG, "click too fast:" + msg);
            return false;
        }

    }
}
