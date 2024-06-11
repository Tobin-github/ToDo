package top.tobin.navigation.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;

import java.lang.reflect.Method;
import java.util.List;

public class AppUtils {

    private static final String TAG = "AppUtils";

    /**
     * 判断应用是否是在后台
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (TextUtils.equals(appProcess.processName, context.getPackageName())) {
                return appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE;
            }
        }
        return false;
    }

    /**
     * 获取屏幕id
     */
    public static int getDisplayId(Context context) {
        try {
            Method method = Context.class.getDeclaredMethod("getDisplay");
            method.setAccessible(true);
            Display display = (Display) method.invoke(context);
            if (display == null) {
                return Display.DEFAULT_DISPLAY;
            }
            Log.i(TAG, "displayId:" + display.getDisplayId());
            return display.getDisplayId();
        } catch (Exception e) {
            Log.i(TAG, "reflect getDisplay fun exception:" + e);
        }
        return Display.DEFAULT_DISPLAY;
    }
}