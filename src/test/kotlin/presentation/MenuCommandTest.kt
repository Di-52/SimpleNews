package presentation

import domain.KeywordsUseCase
import domain.SpecialNewsUseCase
import domain.UsualNewsUseCase
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * @author Demitrist on 22.02.2023
 */

class MenuCommandTest {

    @Test
    fun test_all_news() {
        val usualNews = FakeUsualNewsUseCase()
        val command = MenuCommand.AllNews(usualNews)
        command.execute("")
        assertEquals(expected = 1, actual = usualNews.usualNewsUseCaseCalledCount)
    }

    @Test
    fun test_special_news() {
        val specialNews = FakeSpecialNewsUseCase()
        val command = MenuCommand.SpecialNews(specialNews)
        command.execute("")
        assertEquals(expected = 1, actual = specialNews.newsCalledCount)
        assertEquals(expected = 0, actual = specialNews.keywordCalls.size)
        assertEquals(expected = 0, actual = specialNews.sortOrderCalls.size)
    }

    @Test
    fun test_add_keyword() {
        val specialNews = FakeSpecialNewsUseCase()
        val command = MenuCommand.AddKeyword(specialNews)
        command.execute("1. Key first")
        assertEquals(expected = 1, actual = specialNews.keywordCalls.size)
        assertEquals(expected = "Key first", actual = specialNews.keywordCalls[0])
        assertEquals(expected = 0, actual = specialNews.newsCalledCount)
        assertEquals(expected = 0, actual = specialNews.sortOrderCalls.size)
    }

    @Test
    fun test_sort_order() {
        val specialNews = FakeSpecialNewsUseCase()
        val command = MenuCommand.SortOrder(specialNews)
        command.execute("")
        assertEquals(expected = 1, actual = specialNews.sortOrderCalls.size)
        assertEquals(expected = false, actual = specialNews.sortOrderCalls[0])
        command.execute("Descending")
        assertEquals(expected = 2, actual = specialNews.sortOrderCalls.size)
        assertEquals(expected = true, actual = specialNews.sortOrderCalls[1])
        assertEquals(expected = 0, actual = specialNews.keywordCalls.size)
        assertEquals(expected = 0, actual = specialNews.newsCalledCount)
    }

    @Test
    fun test_menu_keywords() {
        val keywords = FakeKeywordsUseCase()
        val factory = FakeFactory()
        val menu = FakeMenu()
        factory.resultMenu = menu
        val command = MenuCommand.MenuKeywords(useCase = keywords, menuFactory = factory)
        command.execute("")

        assertEquals(expected = 1,actual = keywords.keywordsCalledCount)
    }

    @Test
    fun test_menu_sort_order(){
        val menu = FakeMenu()
        val command = MenuCommand.MenuSortOrder(menu)
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

    private class FakeFactory : MenuFactory.Keywords {
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

    private class FakeKeywordsUseCase : KeywordsUseCase {
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

        override fun sort(orderDesc: Boolean) {
            sortOrderCalls.add(orderDesc)
        }

        override fun keyword(key: String) {
            keywordCalls.add(key)
        }
    }

    private class FakeUsualNewsUseCase : UsualNewsUseCase {
        var usualNewsUseCaseCalledCount = 0
        override fun news() {
            usualNewsUseCaseCalledCount++
        }
    }
}