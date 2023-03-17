package presentation.menu

import domain.SetKeywordUseCase

/**
 * @author Demitrist on 10.03.2023
 **/

class AddKeywordMenuCommand(private val useCase: SetKeywordUseCase) : MenuCommand {

    override fun execute(param: String) {
        val temp = param.split(".", limit = 3)
        useCase.keyword(key = temp[1].trim())
    }
}