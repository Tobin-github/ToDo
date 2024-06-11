package top.tobin.accounting.comp

import androidx.fragment.app.Fragment
import top.tobin.accounting.AccountingFragment
import top.tobin.accounting.R
import top.tobin.common.base.Initializer
import top.tobin.component.bean.NavBean
import top.tobin.component.service.IBusinessComp

class AccountingBusinessComp : IBusinessComp {
    override fun getBusEnterPage(): Fragment {
        return AccountingFragment.newInstance()
    }

    override fun getNavBean(isNight: Boolean): NavBean {
        return NavBean(
            Initializer.application.getString(R.string.nav_accounting),
            R.drawable.nav_accounting,
            R.drawable.nav_accounting_h
        )
    }

}