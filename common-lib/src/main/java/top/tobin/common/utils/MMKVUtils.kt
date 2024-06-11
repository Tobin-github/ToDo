package top.tobin.common.utils

import com.tencent.mmkv.MMKV
import top.tobin.common.preferences.IMMKVOwner
import top.tobin.common.preferences.MMKVOwner
import top.tobin.common.preferences.asLiveData
import top.tobin.common.preferences.mmkvBool
import top.tobin.common.preferences.mmkvString

object MMKVUtils : IMMKVOwner by MMKVOwner(mmapID = "TodoMMKV") {
//    val isNightMode by mmkvBool().asLiveData()
    var isNightMode by mmkvBool()
    var language by mmkvString(default = "zh")

    fun removeValueForKey() {
        kv.removeValueForKey(::language.name) // 建议修改了默认值才移除 key，否则赋值操作更简洁
        kv.clearAll()
    }

    // MMKV 默认是单进程模式，如果你需要多进程支持
//    override val kv: MMKV = MMKV.mmkvWithID(mmapID, MMKV.MULTI_PROCESS_MODE)


    //MMKV 默认明文存储所有 key-value，依赖 Android 系统的沙盒机制保证文件加密。如果你担心信息泄露，你可以选择加密 MMKV。
//    private const val CRYPT_KEY = "My-Encrypt-Key"
//    override val kv: MMKV = MMKV.mmkvWithID(mmapID, MMKV.SINGLE_PROCESS_MODE, CRYPT_KEY)
}