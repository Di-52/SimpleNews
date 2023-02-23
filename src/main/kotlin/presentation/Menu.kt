package presentation

/**
 * @author Demitrist on 15.02.2023
 **/

interface Menu {

    fun show()
    fun showForResult()

    class Base(
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
            items[result-1].action()
        }
    }
}