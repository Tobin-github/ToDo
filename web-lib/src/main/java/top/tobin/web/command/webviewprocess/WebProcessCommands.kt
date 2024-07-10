package top.tobin.web.command.webviewprocess

import android.content.Context
import top.tobin.common.utils.LogUtil
import top.tobin.web.command.base.ResultBack

import top.tobin.web.command.base.Command
import top.tobin.web.command.base.Commands
import top.tobin.web.utils.WebConstants


/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: LocalCommands.
 */
class WebProcessCommands : Commands() {

    private fun registerCommands() {
        registerCommand(showToastCommand)
    }

    fun unRegisterCommands() {
        unRegisterCommand(showToastCommand)
    }

    /**
     * 显示Toast信息
     */
    private val showToastCommand: Command = object : Command {
        override fun name(): String {
            return "showToast"
        }

        override fun exec(
            context: Context?,
            params: MutableMap<String, Any>,
            resultBack: ResultBack
        ) {
            LogUtil.i(msg = "showToastCommand: $params")
            params[WebConstants.NATIVE2WEB_CALLBACK] =
                params[WebConstants.WEB2NATIVE_CALLBACK] as Any
            resultBack.onResult(WebConstants.SUCCESS, name(), params)
        }
    }

    init {
        registerCommands()
    }
}