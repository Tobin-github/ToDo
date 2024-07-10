package top.tobin.web.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 错误信息.
 */
@Parcelize
data class AidlError(
    var code: Int = 0,
    var message: String? = null,
    var extra: String? = null,
) : Parcelable
