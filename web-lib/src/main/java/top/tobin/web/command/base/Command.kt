package top.tobin.web.command.base

import android.content.Context

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: Command.
 */
interface Command {
    fun name(): String
    fun exec(context: Context?, params: MutableMap<String, Any>, resultBack: ResultBack)

    companion object {
        const val COMMAND_UPDATE_TITLE = "tobin_webview_update_title"
        const val COMMAND_UPDATE_TITLE_PARAMS = "tobin_webview_update_title_params"
    }
}