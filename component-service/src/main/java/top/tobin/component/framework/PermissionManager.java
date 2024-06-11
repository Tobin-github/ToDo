package top.tobin.component.framework;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import top.tobin.common.base.Initializer;

public class PermissionManager {

    private static PermissionManager sInstance = new PermissionManager();
    private boolean mIsDrawOverlayRequired = true;
    private String[] mPermissions;
    private List<PermissionGrantedListener> mPermissionListeners;

    private PermissionManager() {
        mPermissionListeners = new CopyOnWriteArrayList<>();
    }

    public static PermissionManager getInstance() {
        return sInstance;
    }

    public void setReqPermissions(String[] permissions) {
        mPermissions = permissions;
    }

    /**
     * 权限监听
     */
    public void registerPermissionGrantedListener(PermissionGrantedListener listener) {
        if (isPermissionGranted(Initializer.application)) {
            listener.onAllPermissionsGranted();
            return;
        }
        if (listener != null && !mPermissionListeners.contains(listener)) {
            mPermissionListeners.add(listener);
        }
    }

    /**
     * 反注册权限监听
     */
    public void unregisterPermissionGrantedListener(PermissionGrantedListener listener) {
        if (listener != null) {
            mPermissionListeners.remove(listener);
        }
    }

    /**
     * 检查是否已授权
     */
    public boolean isPermissionGranted(Context context) {
        boolean allGranted = true;
        if (mPermissions != null && mPermissions.length != 0) {
            for (String permission : mPermissions) {
                int ret = ActivityCompat.checkSelfPermission(context, permission);
                if (ret == PackageManager.PERMISSION_DENIED) {
                    allGranted = false;
                    Log.d("PermissionManager", " Denied permission:" + permission);
                    break;
                }
            }
        }
        return allGranted;
    }

    /**
     * 申请权限
     */
    public void requestPermission(Activity activity) {
        if (mPermissions == null || mPermissions.length == 0) {
            return;
        }
        List<String> permissionList = new ArrayList<>();
        for (String permission : mPermissions) {
            int ret = ActivityCompat.checkSelfPermission(activity, permission);
            if (ret == PackageManager.PERMISSION_DENIED) {
                permissionList.add(permission);
            }
        }
        Log.d("requestPermission", "PERMISSION_DENIED :" + permissionList);
        if (permissionList.size() > 0) {
            ActivityCompat.requestPermissions(activity, permissionList.toArray(new String[permissionList.size()]), 0);
        }
    }

    /**
     * 处理权限申请回调
     */
    public boolean handleRequestPermissionsResult(Context context, String[] permissions, int[] grantResults) {
        boolean allGranted = isPermissionGranted(context);
        if (allGranted) {
            for (PermissionGrantedListener listener : mPermissionListeners) {
                listener.onAllPermissionsGranted();
            }
        }
        return allGranted;
    }

    /**
     * 检查悬浮窗权限
     */
    public boolean canDrawOverlay(Context context) {
        if (mIsDrawOverlayRequired && !Settings.canDrawOverlays(context)) {
            return false;
        }
        return true;
    }

    public interface PermissionGrantedListener {

        void onAllPermissionsGranted();
    }
}
