package top.tobin.component.service;

/**
 * 业务模块初始化接口
 */
public interface IInitTask{
    void onCreate();

    default void onStop(){

    }
}