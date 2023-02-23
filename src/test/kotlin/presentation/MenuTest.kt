package presentation

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * @author Demitrist on 22.02.2023
 */

class MenuTest {

    @Test
    fun test_menu_show() {
        val item1 = FakeItem()
        val item2 = FakeItem()
        val item3 = FakeItem()
        val items = listOf(item1, item2, item3)
        val reader = FakeUserInput()
        val menu = Menu.Base(name = "menu name", items = items, userInputReader = reader)

        menu.show()
        assertEquals(expected = 1, actual = item1.showCalledCount)
        assertEquals(expected = 0, actual = item1.actionCalledCount)
        assertEquals(expected = 1, actual = item2.showCalledCount)
        assertEquals(expected = 0, actual = item2.actionCalledCount)
        assertEquals(expected = 1, actual = item3.showCalledCount)
        assertEquals(expected = 0, actual = item3.actionCalledCount)
        assertEquals(expected = 0, actual = reader.waitCalledCount)
    }

    @Test
    fun test_menu_show_for_result() {
        val item1 = FakeItem()
        val item2 = FakeItem()
        val item3 = FakeItem()
        val items = listOf(item1, item2, item3)
        val reader = FakeUserInput()
        reader.result = 2
        val menu = Menu.Base(name = "menu name", items = items, userInputReader = reader)

        menu.showForResult()

        assertEquals(expected = 1, actual = item1.showCalledCount)
        assertEquals(expected = 0, actual = item1.actionCalledCount)
        assertEquals(expected = 1, actual = item2.showCalledCount)
        assertEquals(expected = 1, actual = item2.actionCalledCount)
        assertEquals(expected = 1, actual = item3.showCalledCount)
        assertEquals(expected = 0, actual = item3.actionCalledCount)
        assertEquals(expected = 1, actual = reader.waitCalledCount)
        assertEquals(expected = 1, actual = reader.waitFrom)
        assertEquals(expected = 3, actual = reader.waitTo)
    }

    private class FakeUserInput : UserInput {
        var result: Int? = null
        var waitCalledCount = 0
        var waitFrom = 0
        var waitTo = 0
        override fun wait(valFrom: Int, valTo: Int): Int {
            waitFrom = valFrom
            waitTo = valTo
            waitCalledCount++
            return result!!
        }

    }

    private class FakeItem : MenuItem {
        var showCalledCount = 0
        var actionCalledCount = 0
        override fun show() {
            showCalledCount++
        }

        override fun action() {
            actionCalledCount++
        }
    }

}