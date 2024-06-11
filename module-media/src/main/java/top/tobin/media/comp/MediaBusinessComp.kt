package top.tobin.media.comp

import androidx.fragment.app.Fragment
import top.tobin.common.base.Initializer
import top.tobin.component.bean.NavBean
import top.tobin.component.service.IBusinessComp
import top.tobin.component.service.IInitTask
import top.tobin.component.service.IPermission
import top.tobin.media.MediaFragment
import top.tobin.media.R

class MediaBusinessComp : IBusinessComp {
    override fun getBusEnterPage(): Fragment {
        return MediaFragment.newInstance()
    }

    override fun getNavBean(isNight: Boolean): NavBean {
        return NavBean(
            Initializer.application.getString(R.string.nav_media),
            R.drawable.nav_media,
            R.drawable.nav_media_h
        )
    }

    override fun getInitTask(): IInitTask? {
        return super<IBusinessComp>.getInitTask()
    }

    override fun getPermission(): IPermission? {
        return super<IBusinessComp>.getPermission()
    }
}