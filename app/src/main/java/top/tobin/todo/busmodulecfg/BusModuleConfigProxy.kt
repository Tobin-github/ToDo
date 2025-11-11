package top.tobin.todo.busmodulecfg

class BusModuleConfigProxy private constructor() : IBusModuleConfig {
    private val moduleConfig: IBusModuleConfig = ComBusModuleConfig()

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = BusModuleConfigProxy()
    }

    override fun getBusModules(): List<BusModule> {
        return moduleConfig.getBusModules()
    }

}