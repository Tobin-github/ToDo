package top.tobin.shared.data.remote

/**
 * Created by Tobin
 * Email: junbin.li@qq.com
 * Description: 处理成功或者失败的情况.
 */

sealed class DataResult<out T : Any> {
    data class Success<out T: Any>(val value: T) : DataResult<T>()

    data class Failure(val throwable: Throwable) : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$value]"
            is Failure -> "Failure[exception=$throwable]"
        }
    }
}

inline fun <reified T : Any> DataResult<T>.doSuccess(success: (T) -> Unit) {
    if (this is DataResult.Success) {
        success(value)
    }
}

inline fun <reified T : Any> DataResult<T>.doFailure(failure: (Throwable) -> Unit) {
    if (this is DataResult.Failure) {
        failure(throwable)
    }
}
