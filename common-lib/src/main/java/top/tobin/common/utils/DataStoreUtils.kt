package top.tobin.common.utils

import top.tobin.common.preferences.DataStoreOwner
import top.tobin.common.preferences.IDataStoreOwner

object DataStoreUtils : IDataStoreOwner by DataStoreOwner(name = "TodoDataStore") {
    val TEST_DATA by stringPreference()

}
