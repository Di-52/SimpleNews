package presentation.menu

import presentation.UserInput

/**
 * @author Demitrist on 10.03.2023
 **/

class MenuImpl(
    private val name: String,
    private val items: List<MenuItem>,
    private val userInputReader: UserInput
) : Menu {

    override fun show() {
        println(name)
        items.forEach { menuItem ->
            menuItem.show()
        }
    }

    override fun showForResult() {
        show()
        val result = userInputReader.wait(valFrom = 1, items.size)
        items[result - 1].action()
    }
}