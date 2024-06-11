package top.tobin.todo.busmodulecfg

import top.tobin.component.service.IMediaComp

class BusModuleConfigProxy private constructor() : IBusModuleConfig {
    private val moduleConfig: IBusModuleConfig

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = BusModuleConfigProxy()
    }

    init {
        moduleConfig = ComBusModuleConfig()
    }

    override fun getBusModules(): List<BusModule> {
        return moduleConfig.getBusModules()
    }

}