package presentation

import domain.*
import org.junit.jupiter.api.Test
import presentation.menu.*
import kotlin.test.assertEquals

/**
 * @author Demitrist on 22.02.2023
 */

class MenuCommandTest {

    @Test
    fun test_all_news() {
        val usualNews = FakeUsualNewsUseCase()
        val command = AllNewsMenuCommand(usualNews)
        command.execute("")
        assertEquals(expected = 1, actual = usualNews.usualNewsUseCaseCalledCount)
    }

    @Test
    fun test_special_news() {
        val specialNews = FakeFetchNewsUseCase()
        val command = SpecialNewsMenuCommand(specialNews)
        command.execute("")
        assertEquals(expected = 1, actual = specialNews.newsCalledCount)
    }

    @Test
    fun test_add_keyword() {
        val specialNews = FakeSetKeyword()
        val command = AddKeywordMenuCommand(specialNews)
        command.execute("1. Key first")
        assertEquals(expected = 1, actual = specialNews.setKeywordCalls.size)
        assertEquals(expected = "Key first", actual = specialNews.setKeywordCalls[0])
    }

    @Test
    fun test_sort_order() {
        val specialNews = FakeSetSortOrder()
        val command = ChangeSortOrderMenuCommand(specialNews)
        command.execute("")
        assertEquals(expected = 1, actual = specialNews.setSortOrderCalls.size)
        assertEquals(expected = false, actual = specialNews.setSortOrderCalls[0])
        command.execute("Descending")
        assertEquals(expected = 2, actual = specialNews.setSortOrderCalls.size)
        assertEquals(expected = true, actual = specialNews.setSortOrderCalls[1])
    }

    @Test
    fun test_menu_keywords() {
        val keywords = FakeKeywordsUseCase()
        val factory = FakeFactory()
        val menu = FakeMenu()
        factory.resultMenu = menu
        val command = KeywordsMenuCommand(useCase = keywords, menuFactory = factory)
        command.execute("")

        assertEquals(expected = 1,actual = keywords.keywordsCalledCount)
    }

    @Test
    fun test_menu_sort_order(){
        val menu = FakeMenu()
        val command = SortOrderMenuCommand(menu)
        command.execute("")

        assertEquals(expected = 1, menu.menuCalledCount)
    }

    private class FakeMenu : Menu {
        var menuCalledCount = 0

        override fun show() {
            menuCalledCount++
        }

        override fun showForResult() {
            menuCalledCount++
        }
    }

    private class FakeFactory : KeywordsMenuFactory {
        var resultMenu: FakeMenu? = null
        var putKeywordsCalledCount = 0

        override fun putKeywords(keys: List<String>) {
            putKeywordsCalledCount++
        }

        override fun menu(name: String): Menu {
            return resultMenu!!
        }

        override fun clear() = Unit
    }

    private class FakeKeywordsUseCase : FetchKeywordsUseCase {
        var keywordsCalledCount = 0
        override fun keywords(block: (List<String>) -> Unit) {
            keywordsCalledCount++
        }
    }

    private class FakeSpecialNewsUseCase : SpecialNewsUseCase {
        var newsCalledCount = 0
        val sortOrderCalls = mutableListOf<Boolean>()
        var keywordCalls = mutableListOf<String>()

        override fun news() {
            newsCalledCount++
        }
    }

    private class FakeFetchNewsUseCase : FetchNewsUseCase{
        var newsCalledCount = 0

        override fun news() {
            newsCalledCount++
        }

    }

    private class FakeSetKeyword : SetKeywordUseCase {
        val setKeywordCalls = mutableListOf<String>()

        override fun keyword(key: String) {
            setKeywordCalls.add(key)
        }
    }

    private class FakeSetSortOrder: SetSortOrderUseCase {
        val setSortOrderCalls = mutableListOf<Boolean>()

        override fun setSortOrder(sortOrderDesc: Boolean) {
            setSortOrderCalls.add(sortOrderDesc)
        }
    }

    private class FakeUsualNewsUseCase : FetchNewsUseCase {
        var usualNewsUseCaseCalledCount = 0
        override fun news() {
            usualNewsUseCaseCalledCount++
        }
    }
}