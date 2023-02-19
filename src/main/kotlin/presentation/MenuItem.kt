package presentation

/**
 * @author Demitrist on 15.02.2023
 **/

interface MenuItem {

    fun show()
    fun action()

    class Base(private val text: String, private val command: MenuCommand) : MenuItem {

        override fun show() {
            println(text)
        }
        override fun action() = command.execute(param = text)
    }
}