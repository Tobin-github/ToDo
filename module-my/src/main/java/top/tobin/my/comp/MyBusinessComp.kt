package top.tobin.my.comp

import androidx.fragment.app.Fragment
import top.tobin.common.base.Initializer
import top.tobin.component.bean.NavBean
import top.tobin.component.service.IBusinessComp
import top.tobin.component.service.IInitTask
import top.tobin.component.service.IPermission
import top.tobin.my.MyFragment
import top.tobin.my.R

class MyBusinessComp : IBusinessComp {
    override fun getBusEnterPage(): Fragment {
        return MyFragment.newInstance()
    }

    override fun getNavBean(isNight: Boolean): NavBean {
        return NavBean(
            Initializer.application.getString(R.string.nav_my),
            R.drawable.nav_my,
            R.drawable.nav_my_h
        )
    }

}