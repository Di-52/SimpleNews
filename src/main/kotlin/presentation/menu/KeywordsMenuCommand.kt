package presentation.menu

import domain.FetchKeywordsUseCase

/**
 * @author Demitrist on 10.03.2023
 **/

class KeywordsMenuCommand(
    private val useCase: FetchKeywordsUseCase,
    private val menuFactory: KeywordsMenuFactory
) :
    MenuCommand {

    override fun execute(param: String) {
        useCase.keywords() {
            menuFactory.putKeywords(it)
            val keywordsMenu = menuFactory.menu("Keywords: ")
            keywordsMenu.showForResult()
        }
    }
}