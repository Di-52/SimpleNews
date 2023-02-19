package data

import NewsListDomain
import data.models.NewsListData
import data.models.ResultData
import domain.models.DomainError
import domain.models.ResultDomain
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * @author Demitrist on 17.02.2023
 */

class CloudDataSourceTest {

    @Test
    fun test_return_success() = runBlocking {
        val service = FakeService()
        val dataSource = CloudDataSource(service)
        val newsList = NewsListData(name = "name", location = "location", news = emptyList())
        service.result = ResultData.Success(newsList)

        val expected = ResultData.Success(newsList)
        val actual = dataSource.fetchData()

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_return_fail() = runBlocking {
        val service = FakeService()
        val result = ResultData.Fail(e = Exception("error"))
        service.result = result

        val dataSource = CloudDataSource(service)
        val expected = result
        val actual = dataSource.fetchData()

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_throw_exception() = runBlocking {
        val service = FakeService()
        val dataSource = CloudDataSource(service)
        val mapper = FakeFailMapper()

        val expected = ResultDomain.Fail(DomainError.GenericError("java.lang.NullPointerException"))
        val actual = dataSource.fetchData().map(mapper)

        assertEquals(expected = expected, actual = actual)
    }

    @Test
    fun test_long_response() = runBlocking {
        val service = NoResponseService()
        val dataSource = CloudDataSource(service)
        val mapper = FakeFailMapper()

        val expected = ResultDomain.Fail(DomainError.GenericError("kotlinx.coroutines.TimeoutCancellationException"))
        val actual = dataSource.fetchData().map(mapper)

        assertEquals(expected = expected, actual = actual)
    }
}

private class FakeService : CloudService {
    var result: ResultData? = null

    override suspend fun fetch(): ResultData {
        return result!!
    }
}

private class NoResponseService : CloudService {

    override suspend fun fetch(): ResultData {
        delay(10000)
        return ResultData.Fail(e = Exception())
    }
}

private class FakeFailMapper : ResultDataToDomainMapper {

    override fun map(data: NewsListData): ResultDomain {
        return ResultDomain.Success(NewsListDomain("", "", emptyList(), emptyList()))
    }

    override fun map(e: Exception): ResultDomain {
        return ResultDomain.Fail(DomainError.GenericError(message = e.javaClass.name))
    }
}