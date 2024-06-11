package top.tobin.gallery.comp

import androidx.fragment.app.Fragment
import top.tobin.common.base.Initializer
import top.tobin.component.bean.NavBean
import top.tobin.component.service.IBusinessComp
import top.tobin.component.service.IInitTask
import top.tobin.component.service.IPermission
import top.tobin.gallery.GalleryFragment
import top.tobin.gallery.R

class GalleryBusinessComp : IBusinessComp {
    override fun getBusEnterPage(): Fragment {
        return GalleryFragment.newInstance()
    }

    override fun getNavBean(isNight: Boolean): NavBean {
        return NavBean(
            Initializer.application.getString(R.string.nav_gallery),
            R.drawable.nav_gallery,
            R.drawable.nav_gallery_h
        )
    }

    override fun getInitTask(): IInitTask? {
        return super<IBusinessComp>.getInitTask()
    }

    override fun getPermission(): IPermission? {
        return super<IBusinessComp>.getPermission()
    }
}