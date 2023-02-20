package data

import NewsListDomain
import data.models.NewsListData
import data.models.ResultData
import domain.models.DomainError
import domain.models.ResultDomain
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * @author Demitrist on 19.02.2023
 */

class BaseNewsRepositoryTest {

    private val resultDomainSuccess = ResultDomain.Success(
        news = NewsListDomain(
            name = "name",
            location = "location",
            news = emptyList(),
            keywords = emptyList()
        )
    )

    private val resultDataSuccess = ResultData.Success(
        data = NewsListData(
            name = "name",
            location = "location",
            news = emptyList()
        )
    )

    /*
    request 1: cache is empty, cloud return success result
    request 2: cache is contain previous result from cloud, cloud not touched
     */
    @Test
    fun cloud_first_success() = runBlocking {
        val expected = resultDomainSuccess
        val mapper = FakeMapper()
        mapper.result = expected
        val cloudDataSource = FakeCloudDataSource()
        cloudDataSource.result = resultDataSuccess
        val cacheDataSource = FakeCacheDataSource()
        val repository = BaseNewsRepository(
            cloud = cloudDataSource,
            cache = cacheDataSource,
            mapper = mapper
        )

        var actual = repository.fetchNews()

        assertEquals(expected = 1, actual = cloudDataSource.cloudDataSourceCalledCount)
        assertEquals(expected = 1, actual = cacheDataSource.cacheDataSourceSaveCalledCount)
        assertEquals(expected = 1, mapper.mapperCalledCount)
        assertEquals(expected = expected, actual = actual)

        actual = repository.fetchNews()

        assertEquals(expected = 1, actual = cloudDataSource.cloudDataSourceCalledCount)
        assertEquals(expected = 1, actual = cacheDataSource.cacheDataSourceFetchCalledCount)
        assertEquals(expected = 1, actual = cacheDataSource.cacheDataSourceSaveCalledCount)
        assertEquals(expected = 2, actual = mapper.mapperCalledCount)
        assertEquals(expected = expected, actual = actual)
    }

    /*
    request 1: cache is empty, cloud return error
    request 2: cache as before empty, cloud return success
    request 3: cache contain success result from request2, cloud not touched
     */
    @Test
    fun test_cloud_firs_fail_second_success() = runBlocking {
        val expected = ResultDomain.Fail(error = DomainError.ConnectionError(message = ""))
        val mapper = FakeMapper()
        mapper.result = expected
        val cloudDataSource = FakeCloudDataSource()
        val cacheDataSource = FakeCacheDataSource()
        val repository = BaseNewsRepository(
            cloud = cloudDataSource,
            cache = cacheDataSource,
            mapper = mapper
        )

        cloudDataSource.result = ResultData.Fail(e = Exception())
        var actual = repository.fetchNews()

        assertEquals(expected = 1, actual = cloudDataSource.cloudDataSourceCalledCount)
        assertEquals(expected = 0, actual = cacheDataSource.cacheDataSourceSaveCalledCount)
        assertEquals(expected = 1, mapper.mapperCalledCount)
        assertEquals(expected = 1, mapper.errorsList.size)
        assertEquals(expected = expected, actual = actual)

        cloudDataSource.result = resultDataSuccess
        actual = repository.fetchNews()

        assertEquals(expected = 2, actual = cloudDataSource.cloudDataSourceCalledCount)
        assertEquals(expected = 1, actual = cacheDataSource.cacheDataSourceSaveCalledCount)
        assertEquals(expected = 2, actual = mapper.mapperCalledCount)
        assertEquals(expected = 1, actual = mapper.dataList.size)
        assertEquals(expected = expected, actual = actual)

        actual = repository.fetchNews()

        assertEquals(expected = 2, actual = cloudDataSource.cloudDataSourceCalledCount)
        assertEquals(expected = 1, actual = cacheDataSource.cacheDataSourceFetchCalledCount)
        assertEquals(expected = 1, actual = cacheDataSource.cacheDataSourceSaveCalledCount)
        assertEquals(expected = 3, actual = mapper.mapperCalledCount)
        assertEquals(expected = 2, actual = mapper.dataList.size)
        assertEquals(expected = expected, actual = actual)
    }
}

private class FakeMapper : ResultDataToDomainMapper {
    var result: ResultDomain? = null
    var dataList = mutableListOf<NewsListData>()
    var errorsList = mutableListOf<java.lang.Exception>()
    var mapperCalledCount = 0

    override fun map(data: NewsListData): ResultDomain {
        mapperCalledCount++
        dataList.add(data)
        return result!!
    }

    override fun map(e: Exception): ResultDomain {
        mapperCalledCount++
        errorsList.add(e)
        return result!!
    }
}

private class FakeCloudDataSource : DataSource {
    var result: ResultData? = null
    var cloudDataSourceCalledCount = 0

    override suspend fun fetchData(): ResultData {
        cloudDataSourceCalledCount++
        return result!!
    }
}

private class FakeCacheDataSource() : DataSource.Mutable {
    var data: ResultData? = null
    var cacheDataSourceFetchCalledCount = 0
    var cacheDataSourceSaveCalledCount = 0

    override fun haveData() = data != null
    override suspend fun fetchData(): ResultData {
        cacheDataSourceFetchCalledCount++
        return data!!
    }

    override fun save(dataToSave: ResultData) {
        cacheDataSourceSaveCalledCount++
        data = dataToSave
    }
}