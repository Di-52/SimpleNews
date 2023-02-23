package domain.models

import NewsItemDomain
import NewsListDomain
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

/**
 * @author Demitrist on 21.02.2023
 */

class NewsListDomainTest {
    private val date = LocalDateTime.now()
    private val news = NewsListDomain(
        name = "name",
        location = "location",
        news = listOf(
            item(2, listOf("k2", "k3")),
            item(1, listOf("k1")),
            item(3, emptyList())
        ),
        keywords = listOf("k2", "k3", "k1")
    )

    @Test
    fun test_one_filter() {
        val fakeFilter = FakeFilter()
        fakeFilter.trueResultsCount = 2
        val filters = mutableListOf(fakeFilter)
        val filter = NewsListDomain.Mapper.Filter(filters)
        val expected = NewsListDomain(
            name = "name",
            location = "location",
            news = listOf(
                item(2, listOf("k2", "k3")),
                item(1, listOf("k1"))
            ),
            keywords = listOf("k2", "k3", "k1")
        )
        val result = news.map(filter)
        assertEquals(expected = expected, actual = result)
        filters.forEach {
            assertEquals(expected = 3, actual = it.inputs.size)
            assertEquals(expected = listOf("t2","t1","t3"), actual = it.inputs.toList())
        }
    }

    @Test
    fun test_two_filters() {
        val fakeFilter1 = FakeFilter()
        fakeFilter1.trueResultsCount = 2
        val fakeFilter2 = FakeFilter()
        fakeFilter2.trueResultsCount = 1
        val filters = mutableListOf(fakeFilter1, fakeFilter2)
        val filter = NewsListDomain.Mapper.Filter(filters)
        val expected = NewsListDomain(
            name = "name",
            location = "location",
            news = listOf(
                item(2, listOf("k2", "k3"))
            ),
            keywords = listOf("k2", "k3", "k1")
        )
        val result = news.map(filter)
        assertEquals(expected = expected, actual = result)
        filters.forEach {
            assertEquals(expected = true, actual = it.inputs.size > 0)
        }
    }

    @Test
    fun test_sorting_by_desc() {
        val sorter = NewsListDomain.Mapper.Sorter(sortOrderDesc = true)
        val expected = NewsListDomain(
            name = "name",
            location = "location",
            news = listOf(
                item(1, listOf("k1")),
                item(2, listOf("k2", "k3")),
                item(3, emptyList())
            ),
            keywords = listOf("k2", "k3", "k1")
        )
        val actual = news.map(sorter)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_sorting_by_asc() {
        val sorter = NewsListDomain.Mapper.Sorter(sortOrderDesc = false)
        val expected = NewsListDomain(
            name = "name",
            location = "location",
            news = listOf(
                item(3, emptyList()),
                item(2, listOf("k2", "k3")),
                item(1, listOf("k1"))
            ),
            keywords = listOf("k2", "k3", "k1")
        )
        val actual = news.map(sorter)

        assertEquals(expected = expected, actual = actual)
    }

    private fun item(id: Int, keys: List<String>): NewsItemDomain {
        return NewsItemDomain(
            id = id,
            title = "t$id",
            description = "d$id",
            date = date.plusNanos(id.toLong()),
            keywords = keys,
            visible = true
        )
    }

    private class FakeFilter : NewsItemDomain.Mapper<Boolean> {
        val inputs = mutableListOf<String>()
        var trueResultsCount: Int? = null

        override fun map(
            id: Int,
            title: String,
            description: String,
            date: LocalDateTime,
            keywords: List<String>,
            visible: Boolean
        ): Boolean {
            inputs.add(title)
            return inputs.size <= trueResultsCount!!
        }
    }
}