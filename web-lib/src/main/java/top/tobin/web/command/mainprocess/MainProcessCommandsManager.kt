package top.tobin.web.command.mainprocess

import android.content.Context
import top.tobin.web.command.base.ResultBack
import top.tobin.web.utils.AidlError
import top.tobin.web.utils.WebConstants
import top.tobin.web.utils.WebConstants.ErrorCode
import top.tobin.web.utils.WebConstants.ErrorMessage
import top.tobin.web.command.base.Command

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: 主进程需要执行的Command.
 */
class MainProcessCommandsManager private constructor() {
    private val mainProcessCommands: MainProcessCommands = MainProcessCommands()

    companion object {
        val instance: MainProcessCommandsManager = Holder.instance
    }

    private object Holder {
        val instance = MainProcessCommandsManager()
    }

    /**
     * 动态注册command
     * 应用场景：其他模块在使用WebView的时候，需要增加特定的command，动态加进来
     */
    fun registerCommand(command: Command) {
        mainProcessCommands.registerCommand(command)
    }

    /**
     * 非UI Command 的执行
     */
    fun findAndExecRemoteCommand(
        context: Context?,
        action: String?,
        params: MutableMap<String, Any>,
        resultBack: ResultBack
    ) {
        if (mainProcessCommands.commands[action] != null) {
            mainProcessCommands.commands[action]?.exec(context, params, resultBack)
        } else {
            val aidlError = AidlError(ErrorCode.NO_METHOD, ErrorMessage.NO_METHOD)
            resultBack.onResult(WebConstants.FAILED, action, aidlError)
        }
    }


}