package top.tobin.web.command.webviewprocess

import android.content.Context
import top.tobin.web.command.base.ResultBack
import top.tobin.web.command.base.Command

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: webview进程需要执行的Command.
 */
class WebViewProcessCommandsManager private constructor() {
    private val localCommands: WebProcessCommands = WebProcessCommands()

    private object Holder {
        val instance = WebViewProcessCommandsManager()
    }

    /**
     * 动态注册command
     * 应用场景：其他模块在使用WebView的时候，需要增加特定的command，动态加进来
     */
    fun registerCommand(command: Command) {
        localCommands.registerCommand(command)
    }

    fun unRegisterCommand(command: Command) {
        localCommands.unRegisterCommand(command)
    }

    fun onDestroy() {
        localCommands.unRegisterCommands()
    }

    /**
     * Commands handled by WebView itself, these command does not require aidl.
     */
    fun findAndExecLocalCommand(
        context: Context?,
        action: String?,
        params: MutableMap<String, Any>,
        resultBack: ResultBack
    ) {
        localCommands.commands[action]?.exec(context, params, resultBack)
    }

    fun checkHitLocalCommand(action: String?): Boolean {
        return localCommands.commands[action] != null
    }

    companion object {
        val instance: WebViewProcessCommandsManager
            get() = Holder.instance
    }

}