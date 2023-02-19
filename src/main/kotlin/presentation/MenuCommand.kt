package presentation

import domain.KeywordsUseCase
import domain.SpecialNewsUseCase
import domain.UsualNewsUseCase
import kotlin.system.exitProcess

/**
 * @author Demitrist on 14.02.2023
 **/

interface MenuCommand {

    fun execute(param: String)

    class AllNews(private val useCase: UsualNewsUseCase) : MenuCommand {
        override fun execute(param: String) {
            useCase.news()
        }
    }

    class SpecialNews(private val useCase: SpecialNewsUseCase) : MenuCommand {
        override fun execute(param: String) {
            useCase.news()
        }
    }

    class AddKeyword(private val useCase: SpecialNewsUseCase) : MenuCommand {
        override fun execute(param: String) {
            val temp = param.split(".", limit = 3)
            useCase.keyword(key = temp[1].trim())
        }
    }

    class SortOrder(private val useCase: SpecialNewsUseCase) : MenuCommand {
        override fun execute(param: String) {
            val order = param.contains("Desc")
            useCase.sort(orderDesc = order)
        }
    }

    class MenuKeywords(private val useCase: KeywordsUseCase, private val menuFactory: MenuFactory.Keywords) :
        MenuCommand {
        override fun execute(param: String) {
            useCase.keywords(){
                menuFactory.putKeywords(it)
                val keywordsMenu = menuFactory.menu("Keywords: ")
                keywordsMenu.showForResult()
            }
        }
    }

    class MenuSortOrder(private val menu:Menu) : MenuCommand {
        override fun execute(param: String) {
            menu.showForResult()
        }
    }

    class Exit: MenuCommand {
        override fun execute(param: String) {
            exitProcess(1)
        }
    }

    object Empty : MenuCommand {
        override fun execute(param: String) = Unit
    }
}