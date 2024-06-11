package top.tobin.todo.busmodulecfg

import android.text.TextUtils
import android.util.Log
import top.tobin.component.constant.IBusCompConst
import top.tobin.component.service.IMediaComp

interface IBusModuleConfig {
    fun getBusModules(): List<BusModule>

    fun getPageIndexByModuleName(moduleName: String): Int {
        for (busModule in getBusModules()) {
            if (moduleName == busModule.moduleName) {
                return busModule.pageIndex
            }
        }
        return -1
    }

    fun getModuleNameByPageIndex(tabIndex: Int): String? {
        val busModules = getBusModules()
        if (busModules.size > tabIndex) {
            for (busModule in busModules) {
                if (busModule.pageIndex == tabIndex) {
                    return busModule.moduleName
                }
            }
        }
        return ""
    }

    fun getIndexByJumpView(jumpView: String?): Int {
        return if (TextUtils.isEmpty(jumpView)) {
            -1
        } else when (jumpView) {
            "accounting" -> getPageIndexByModuleName(IBusCompConst.ACCOUNTING_MODULE_BUS_NAME)
            "schedule" -> getPageIndexByModuleName(IBusCompConst.SCHEDULE_MODULE_BUS_NAME)
            "media" -> getPageIndexByModuleName(IBusCompConst.MEDIA_MODULE_BUS_NAME)
            "gallery" -> getPageIndexByModuleName(IBusCompConst.GALLERY_MODULE_BUS_NAME)
            "my" -> getPageIndexByModuleName(IBusCompConst.MY_MODULE_BUS_NAME)
            else -> -1
        }
    }

    fun getPageIndexBySourceType(@IMediaComp.MediaType mediaType: String): Int {
        var pageIndex = -1
        if (IMediaComp.MediaType.MEDIA_TYPE_ONLINE == mediaType) {
            pageIndex = getPageIndexByModuleName(IBusCompConst.ACCOUNTING_MODULE_BUS_NAME)
        } else if (IMediaComp.MediaType.MEDIA_TYPE_RADIO == mediaType) {
            pageIndex = getPageIndexByModuleName(IBusCompConst.MY_MODULE_BUS_NAME)
        } else if (IMediaComp.MediaType.MEDIA_TYPE_BT == mediaType) {
            pageIndex = getPageIndexByModuleName(IBusCompConst.MEDIA_MODULE_BUS_NAME)
        } else if (IMediaComp.MediaType.MEDIA_TYPE_USB == mediaType) {
            pageIndex = getPageIndexByModuleName(IBusCompConst.SCHEDULE_MODULE_BUS_NAME)
        } else {
            Log.i("IBusModuleConfig", "current  mediaType: $mediaType")
        }
        return pageIndex
    }
}