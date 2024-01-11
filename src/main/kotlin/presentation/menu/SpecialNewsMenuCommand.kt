package presentation.menu

import domain.FetchNewsUseCase

/**
 * @author Demitrist on 10.03.2023
 **/

class SpecialNewsMenuCommand(private val useCase: FetchNewsUseCase) : MenuCommand {

    override fun execute(param: String) {
        useCase.news()
    }
}