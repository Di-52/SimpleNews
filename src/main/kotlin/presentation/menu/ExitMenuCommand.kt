package presentation.menu

import kotlin.system.exitProcess

/**
 * @author Demitrist on 10.03.2023
 **/

class ExitMenuCommand : MenuCommand {

    override fun execute(param: String) {
        exitProcess(1)
    }
}