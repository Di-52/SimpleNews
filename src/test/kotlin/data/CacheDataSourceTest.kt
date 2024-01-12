package data

import data.models.NewsListData
import data.models.ResultData
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * @author Demitrist on 19.02.2023
 */

class CacheDataSourceTest {
    private lateinit var dataSource: MutableDataSource
    private val data = SuccessResultData(NewsListData("name", "location", emptyList()))
    private val data2 = SuccessResultData(NewsListData("name2", "location2", emptyList()))

    @BeforeEach
    fun setup() {
        dataSource = CacheDataSource()
    }

    @Test
    fun test_read_no_data() = runBlocking {
        val expected = FailResultData(NoDataException("Try to read empty cache"))
        val actual = dataSource.fetchData()
        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_read_with_data() = runBlocking {
        val expected = data
        dataSource.save(data)
        val actual = dataSource.fetchData()
        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_save_and_read() = runBlocking {
        var expected = data
        dataSource.save(data)
        var actual = dataSource.fetchData()
        assertEquals(expected = expected, actual = actual)
        expected = data2
        dataSource.save(data2)
        actual = dataSource.fetchData()
        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_check_state() {
        var expected = false
        var actual = dataSource.haveData()
        assertEquals(expected = expected, actual = actual)
        expected = true
        dataSource.save(data)
        actual = dataSource.haveData()
        assertEquals(expected = expected, actual = actual)
    }
}