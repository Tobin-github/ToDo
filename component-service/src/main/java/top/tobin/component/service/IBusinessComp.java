package top.tobin.component.service;

import androidx.fragment.app.Fragment;

import top.tobin.component.bean.NavBean;


/**
 * 组件接入工程,业务实现的相关入口
 */
public interface IBusinessComp {

    /**
     * 获取业务模块的入口界面
     */
    Fragment getBusEnterPage();

    /**
     * 导航栏组件Item数据
     */
    NavBean getNavBean(boolean isNight);

    /**
     * 获取初始化任务
     */
    default IInitTask getInitTask() {
        return null;
    }

    /**
     * 获取权限操作
     */
    default IPermission getPermission() {
        return null;
    }
}
