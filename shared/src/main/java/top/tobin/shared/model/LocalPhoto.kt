package top.tobin.shared.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 本地图片数据模型.
 */
@Parcelize
data class LocalPhoto(
    val id: Long,
    val uri: Uri,
    val displayName: String,
    val dateTaken: Long,
    val size: Long
) : Parcelable
