package domain

import NewsItemDomain
import NewsListDomain
import core.DispatchersList
import domain.models.DomainError
import domain.models.ResultDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Test
import presentation.models.ResultUi
import java.time.LocalDateTime
import kotlin.test.assertEquals

/**
 * @author Demitrist on 20.02.2023
 */

class UsualNewsUseCaseTest {

    private val domainNews = NewsListDomain(
        name = "name",
        location = "location",
        news = listOf(
            NewsItemDomain(
                id = 1,
                title = "title",
                description = "description",
                date = LocalDateTime.now(),
                keywords = listOf("key1", "key2"),
                visible = true
            )
        ),
        keywords = listOf("key1", "key2")
    )
    private val domainError = DomainError.GenericError(message = "")
    private val mapperResult = ResultUi(text = "result")

    @Test
    fun test_with_success_result() {
        val repository = FakeRepository()
        repository.result = ResultDomain.Success(news = domainNews)
        val dispatchers = FakeDispatchers()
        val mapper = FakeMapper()
        mapper.result = mapperResult
        val callback = Callback()
        val useCase = UsualNewsUseCase.Base(
            repository = repository,
            dispatchers = dispatchers,
            uiMapper = mapper,
            output = callback
        )

        useCase.news()

        assertEquals(expected = 1, actual = dispatchers.dispatcherIoCalledCount)
        assertEquals(expected = 1, actual = repository.repositoryCalledCount)
        assertEquals(expected = 1, actual = mapper.inputData.size)
        assertEquals(expected = domainNews, actual = mapper.inputData[0])
        assertEquals(expected = 0, actual = mapper.inputErrors.size)
        assertEquals(expected = 1, actual = dispatchers.dispatcherUiCalledCount)
        assertEquals(expected = 1, actual = callback.invokeCount)
    }

    @Test
    fun test_with_fail_result() {
        val repository = FakeRepository()
        repository.result = ResultDomain.Fail(error = domainError)
        val dispatchers = FakeDispatchers()
        val mapper = FakeMapper()
        mapper.result = mapperResult
        val callback = Callback()
        val useCase = UsualNewsUseCase.Base(
            repository = repository,
            dispatchers = dispatchers,
            uiMapper = mapper,
            output = callback
        )

        useCase.news()

        assertEquals(expected = 1, actual = dispatchers.dispatcherIoCalledCount)
        assertEquals(expected = 1, actual = repository.repositoryCalledCount)
        assertEquals(expected = 0, actual = mapper.inputData.size)
        assertEquals(expected = 1, actual = mapper.inputErrors.size)
        assertEquals(expected = domainError, actual = mapper.inputErrors[0])
        assertEquals(expected = 1, actual = dispatchers.dispatcherUiCalledCount)
        assertEquals(expected = 1, actual = callback.invokeCount)
    }


    private class Callback : (String) -> Unit {
        var invokeCount = 0

        override fun invoke(p1: String) {
            invokeCount++
        }
    }

    private class FakeMapper : ResultDomainToUiMapper {
        var result: ResultUi? = null
        val inputData = mutableListOf<NewsListDomain>()
        val inputErrors = mutableListOf<DomainError>()

        override fun map(data: NewsListDomain): ResultUi {
            inputData.add(data)
            return result!!
        }

        override fun map(error: DomainError): ResultUi {
            inputErrors.add(error)
            return result!!
        }
    }

    private class FakeDispatchers : DispatchersList {
        val dispatcher = TestCoroutineDispatcher()

        var dispatcherIoCalledCount = 0
        override fun io(): CoroutineDispatcher {
            dispatcherIoCalledCount++
            return dispatcher
        }

        var dispatcherUiCalledCount = 0
        override fun ui(): CoroutineDispatcher {
            dispatcherUiCalledCount++
            return dispatcher
        }
    }

    private class FakeRepository : NewsRepository {
        var result: ResultDomain? = null
        var repositoryCalledCount = 0

        override suspend fun fetchNews(): ResultDomain {
            repositoryCalledCount++
            return result!!
        }
    }
}