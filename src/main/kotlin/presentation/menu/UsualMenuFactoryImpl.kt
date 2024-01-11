package presentation.menu

import presentation.UserInput

/**
 * @author Demitrist on 10.03.2023
 **/

class UsualMenuFactoryImpl(private val handler: UserInput) : UsualMenuFactory {
    private val items = mutableListOf<MenuItem>()

    override fun putItem(item: MenuItem) {
        items.add(item)
    }

    override fun menu(name: String): Menu {
        return MenuImpl(name = name, items = items.toList(), userInputReader = handler)
    }

    override fun clear() {
        items.clear()
    }
}