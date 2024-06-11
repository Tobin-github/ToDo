package top.tobin.todo.busmodulecfg

import java.io.Serializable

data class BusModule(
    var pageIndex: Int,
    var moduleName: String,
    var moduleImp: String
) : Serializable