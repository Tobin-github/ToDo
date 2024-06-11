package top.tobin.schedule.comp

import androidx.fragment.app.Fragment
import top.tobin.common.base.Initializer
import top.tobin.component.bean.NavBean
import top.tobin.component.service.IBusinessComp
import top.tobin.component.service.IInitTask
import top.tobin.component.service.IPermission
import top.tobin.schedule.R
import top.tobin.schedule.ScheduleFragment

class ScheduleBusinessComp : IBusinessComp {
    override fun getBusEnterPage(): Fragment {///
        return ScheduleFragment.newInstance()
    }

    override fun getNavBean(isNight: Boolean): NavBean {
        return NavBean(
            Initializer.application.getString(R.string.nav_schedule),
            R.drawable.nav_schedule,
            R.drawable.nav_schedule_h
        )
    }

    override fun getInitTask(): IInitTask? {
        return super<IBusinessComp>.getInitTask()
    }

    override fun getPermission(): IPermission? {
        return super<IBusinessComp>.getPermission()
    }
}