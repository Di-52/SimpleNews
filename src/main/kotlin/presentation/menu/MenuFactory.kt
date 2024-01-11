package presentation.menu

/**
 * @author Demitrist on 15.02.2023
 **/

interface MenuFactory {

    fun menu(name: String): Menu

    fun clear()
}

