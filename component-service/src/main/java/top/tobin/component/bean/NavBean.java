package top.tobin.component.bean;

import androidx.annotation.DrawableRes;

public class NavBean {

    public String navName;
    //默认图标
    public @DrawableRes int normalImg;
    //选中图标
    public @DrawableRes int selectImg;

    public NavBean() {
    }

    public NavBean(String navName, @DrawableRes int normalImg, @DrawableRes int selectImg) {
        this.navName = navName;
        this.normalImg = normalImg;
        this.selectImg = selectImg;
    }
}
