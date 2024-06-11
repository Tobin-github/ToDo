package top.tobin.component.framework;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import top.tobin.component.service.IBusinessComp;

public class BusinessCompManager {
    private static final String TAG = "BusinessCompManager";
    private final Map<String, IBusinessComp> busComps = new ArrayMap<>();

    private static class Holder {
        private static final BusinessCompManager INSTANCE = new BusinessCompManager();
    }

    public static BusinessCompManager getInstance() {
        return Holder.INSTANCE;
    }

    private BusinessCompManager() {
    }

    public boolean isEmpty() {
        return busComps.isEmpty();
    }

    /**
     * 注册组件
     *
     * @param classname 组件名
     */
    public void registerComp(String compName, String classname) {
        if (TextUtils.isEmpty(classname)) {
            return;
        }
        if (busComps.containsKey(compName)) {
            return;
        }
        IBusinessComp busCompImp = createIBusCompImpl(classname);
        if (busCompImp == null) {
            return;
        }
        //注册组件时，初始化该组件
        if (busCompImp.getInitTask() != null) {
            busCompImp.getInitTask().onCreate();
        }
        busComps.put(compName, busCompImp);
    }

    /**
     * 反注册组件
     *
     * @param compName 组件名
     */
    public void unregisterComp(String compName) {
        if (busComps.containsKey(compName)) {
            if (busComps.get(compName).getInitTask() != null) {
                busComps.get(compName).getInitTask().onStop();
            }
            busComps.remove(compName);
        }
    }


    public IBusinessComp getBusComp(String compName) {
        if (busComps.containsKey(compName)) {
            return busComps.get(compName);
        }
        return null;
    }


    /**
     * 获取所有组件的 需要申请权限的列表
     */
    public String[] getAllReqPerms() {
        Set<String> permsList = new HashSet<>();
        for (IBusinessComp businessComp : busComps.values()) {
            if (businessComp.getPermission() != null) {
                permsList.addAll(businessComp.getPermission().getReqPerms());
            }
        }
        return permsList.toArray(new String[permsList.size()]);
    }

    private IBusinessComp createIBusCompImpl(String className) {
        Log.i(TAG, "createIBusCompImpl: " + className);
        try {
            Class<?> clazz = Class.forName(className);
            return (IBusinessComp) clazz.newInstance();
        } catch (Exception e) {
            Log.e(TAG, "createIBusCompImpl:", e);
            return null;
        }
    }
}
