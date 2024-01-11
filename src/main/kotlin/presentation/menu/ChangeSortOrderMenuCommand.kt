package presentation.menu

import domain.SetSortOrderUseCase

/**
 * @author Demitrist on 10.03.2023
 **/

class ChangeSortOrderMenuCommand(private val useCase: SetSortOrderUseCase) : MenuCommand {

    override fun execute(param: String) {
        val order = param.contains("Desc")
        useCase.setSortOrder(sortOrderDesc = order)
    }
}