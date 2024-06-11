package top.tobin.todo.busmodulecfg

import top.tobin.component.constant.IBusCompConst

/**
 * 通用渠道配置
 */
class ComBusModuleConfig : IBusModuleConfig {
    private var busModules: MutableList<BusModule> = ArrayList()
    override fun getBusModules(): List<BusModule> {
        if (busModules.size == 0) {
            busModules.add(
                BusModule(
                    0,
                    IBusCompConst.ACCOUNTING_MODULE_BUS_NAME,
                    IBusCompConst.ACCOUNTING_MODULE_BUS_IMP
                )
            )
            busModules.add(
                BusModule(
                    1,
                    IBusCompConst.GALLERY_MODULE_BUS_NAME,
                    IBusCompConst.GALLERY_MODULE_BUS_IMP
                )
            )
            busModules.add(
                BusModule(
                    2,
                    IBusCompConst.SCHEDULE_MODULE_BUS_NAME,
                    IBusCompConst.SCHEDULE_MODULE_BUS_IMP
                )
            )
            busModules.add(
                BusModule(
                    3,
                    IBusCompConst.MEDIA_MODULE_BUS_NAME,
                    IBusCompConst.MEDIA_MODULE_BUS_IMP
                )
            )

            busModules.add(
                BusModule(
                    4,
                    IBusCompConst.MY_MODULE_BUS_NAME,
                    IBusCompConst.MY_MODULE_BUS_IMP
                )
            )
            busModules.sortWith { o1: BusModule, o2: BusModule -> o1.pageIndex - o2.pageIndex }
        }
        return busModules
    }
}