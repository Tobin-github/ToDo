package top.tobin.web.command.base

import java.util.HashMap

/**
 * Created by Tobin on 2022/5/31.
 * Email: junbin.li@qq.com
 * Description: Commands.
 */
abstract class Commands {
    val commands: HashMap<String, Command> = HashMap()

    fun registerCommand(command: Command) {
        commands[command.name()] = command
    }

    fun unRegisterCommand(command: Command) {
        commands.remove(command.name())
    }

}