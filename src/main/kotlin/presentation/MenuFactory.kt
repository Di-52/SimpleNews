package presentation

/**
 * @author Demitrist on 15.02.2023
 **/

interface MenuFactory {

    fun menu(name: String): Menu
    fun clear()

    interface Usual : MenuFactory {
        fun putItem(item: MenuItem)
    }

    interface Keywords : MenuFactory {
        fun putKeywords(keys: List<String>)
    }

    class Base(private val handler: UserInput) : Usual {
        private val items = mutableListOf<MenuItem>()

        override fun putItem(item: MenuItem) {
            items.add(item)
        }

        override fun menu(name: String): Menu {
            return Menu.Base(name = name, items = items.toList(), userInputReader = handler)
        }

        override fun clear() {
            items.clear()
        }
    }

    class KeywordsMenuFactory(private val handler: UserInput, private val addKeywordCommand: MenuCommand) : Keywords {
        private val items = mutableListOf<MenuItem>()

        override fun putKeywords(keys: List<String>) {
            keys.forEachIndexed { index, item ->
                items.add(MenuItem.Base(text = "${index + 1}. $item", command = addKeywordCommand))
            }
        }

        override fun menu(name: String): Menu {
            return Menu.Base(name = name, items = items.toList(), userInputReader = handler)
        }

        override fun clear() {
            items.clear()
        }
    }
}

