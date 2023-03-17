package presentation.menu

/**
 * @author Demitrist on 10.03.2023
 **/

class MenuItemImpl(private val text: String, private val command: MenuCommand) : MenuItem {

    override fun show() {
        println(text)
    }

    override fun action() = command.execute(param = text)
}