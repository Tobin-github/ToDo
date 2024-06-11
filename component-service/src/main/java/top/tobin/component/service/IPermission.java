package top.tobin.component.service;

import java.util.List;

/**
 * 业务模块权限申请接口
 */
public interface IPermission {
    /**
     * 需要申请的权限列表
     */
    List<String> getReqPerms();
}
