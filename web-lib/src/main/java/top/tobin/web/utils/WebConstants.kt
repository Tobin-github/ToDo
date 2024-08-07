package top.tobin.web.utils

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 常量.
 */
object WebConstants {

    const val CONTINUE = 2 // 继续分发command
    const val SUCCESS = 1 // 成功
    const val FAILED = 0 // 失败
    const val EMPTY = "" // 无返回结果

    const val WEB2NATIVE_CALLBACK = "callback"
    const val NATIVE2WEB_CALLBACK = "callbackName"
    const val INTENT_TAG_TITLE = "title"
    const val INTENT_TAG_URL = "url"
    const val INTENT_TAG_HEADERS = "headers"

    object ErrorCode {
        const val NO_METHOD = -1000
        const val NO_AUTH = -1001
        const val NO_LOGIN = -1002
        const val ERROR_PARAM = -1003
        const val ERROR_EXCEPTION = -1004
    }

    object ErrorMessage {
        const val NO_METHOD = "方法找不到"
        const val NO_AUTH = "方法权限不够"
        const val NO_LOGIN = "尚未登录"
        const val ERROR_PARAM = "参数错误"
        const val ERROR_EXCEPTION = "未知异常"
    }
}