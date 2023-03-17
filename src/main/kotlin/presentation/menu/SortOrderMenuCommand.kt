package presentation.menu

/**
 * @author Demitrist on 10.03.2023
 **/

class SortOrderMenuCommand(private val menu: Menu) : MenuCommand {

    override fun execute(param: String) {
        menu.showForResult()
    }
}