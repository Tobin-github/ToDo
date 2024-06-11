package top.tobin.shared.ext

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: GsonExt.
 */

fun <T> Gson.typedToJson(src: T): String = toJson(src)

inline fun <reified T : Any> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)
