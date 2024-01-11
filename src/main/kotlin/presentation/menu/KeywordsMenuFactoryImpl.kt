package presentation.menu

import presentation.UserInput

/**
 * @author Demitrist on 10.03.2023
 **/

class KeywordsMenuFactoryImpl(
    private val handler: UserInput,
    private val addKeywordCommand: MenuCommand
) : KeywordsMenuFactory {
    private val items = mutableListOf<MenuItem>()

    override fun putKeywords(keys: List<String>) {
        keys.forEachIndexed { index, item ->
            items.add(MenuItemImpl(text = "${index + 1}. $item", command = addKeywordCommand))
        }
    }

    override fun menu(name: String): Menu {
        return MenuImpl(name = name, items = items.toList(), userInputReader = handler)
    }

    override fun clear() {
        items.clear()
    }
}