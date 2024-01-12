package domain.models

import NewsItemDomain
import domain.mappers.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

/**
 * @author Demitrist on 21.02.2023
 */

class NewsItemDomainTest {
    private lateinit var matcher: NewsItemDomainMapper<Boolean>
    private val date = LocalDateTime.now()
    private val item = NewsItemDomain(
        id = 1,
        title = "title",
        description = "desc",
        date = date,
        keywords = listOf("key1", "key2"),
        visible = true
    )
    private val keys = listOf(
        listOf("j", "d", "as"),
        listOf("key1", "key", "k"),
        listOf("key1", "key2")
    )

    @Test
    fun test_news_item_compare_date() {
        var otherDate = date
        var actual = item.compareByDate(otherDate)

        assertEquals(expected = 0, actual = actual)

        otherDate = date.plusNanos(1)
        actual = item.compareByDate(otherDate)

        assertEquals(expected = -1, actual = actual)

        otherDate = date.minusNanos(1)
        actual = item.compareByDate(otherDate)

        assertEquals(expected = 1, actual = actual)
    }

    @Test
    fun test_news_item_keywords() {
        val actual = arrayListOf("key")
        val expected = arrayListOf("key", "key1", "key2")

        item.keywords(actual)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_news_item_match_any_keyword() {
        val expected = listOf(false, true, true)
        keys.forEachIndexed { index, key ->
            matcher = NewsItemMatchAnyKeyword(key)

            val result = item.map(mapper = matcher)

            assertEquals(expected = expected[index], actual = result)
        }
    }

    @Test
    fun test_news_item_match_all_keywords() {
        val expected = listOf(false, false, true)
        keys.forEachIndexed { index, key ->
            matcher = NewsItemMatchAllKeywords(key)

            val result = item.map(mapper = matcher)

            assertEquals(expected = expected[index], actual = result)
        }
    }

    @Test
    fun test_news_item_match_visibility() {
        val expected = true
        matcher = NewsItemMatchVisibility()

        val result = item.map(mapper = matcher)

        assertEquals(expected = expected, actual = result)
    }

    @Test
    fun test_news_item_compare_date_mapper() {
        var comparator = NewsItemCompareDate(other = otherItem(date.plusNanos(1)))
        var result = item.map(comparator)

        assertEquals(expected = 1, actual = result)

        comparator = NewsItemCompareDate(other = otherItem(date.minusNanos(1)))
        result = item.map(comparator)

        assertEquals(expected = -1, actual = result)

        comparator = NewsItemCompareDate(other = otherItem(date))
        result = item.map(comparator)

        assertEquals(expected = 0, actual = result)
    }

    private fun otherItem(dateTime: LocalDateTime): NewsItemDomain {
        return NewsItemDomain(1, "", "", dateTime, emptyList(), true)
    }
}