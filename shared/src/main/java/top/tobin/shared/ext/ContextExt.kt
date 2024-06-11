package top.tobin.shared.ext

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission

/**
 * 判断网络是否连接
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isNetworkConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val status = cm.getNetworkCapabilities(network) ?: return false
    return status.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
}

/**
 * 判断是否是WiFi连接
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isWifiConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val status = cm.getNetworkCapabilities(network) ?: return false
    return status.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
}

/**
 * 判断是否是数据网络连接
 */
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
fun Context.isMobileConnected(): Boolean {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = cm.activeNetwork ?: return false
    val status = cm.getNetworkCapabilities(network) ?: return false
    return status.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
}


inline fun <reified T> String.getEmptyOrDefault(default: () -> T): T {
    return if (isNullOrEmpty() || this == "null") {
        default()
    } else {
        this as T
    }
}
