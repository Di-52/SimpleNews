package domain

import NewsListDomain
import core.DispatchersList
import domain.models.ResultDomain
import domain.models.SuccessResultDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

/**
 * @author Demitrist on 21.02.2023
 */

class KeywordsUseCaseTest {

    @Test
    fun test() {
        val callback = Callback()
        val repository = FakeRepository()
        repository.result = SuccessResultDomain(
            news = NewsListDomain(
                name = "name",
                location = "location",
                news = listOf(),
                keywords = listOf("key1")
            )
        )
        val dispatchers = FakeDispatchers()
        val useCase = FetchKeywordsUseCaseImpl(
            repository = repository,
            dispatchers = dispatchers
        )

        useCase.keywords(callback)

        assertEquals(expected = 1, actual = dispatchers.dispatcherIoCalledCount)
        assertEquals(expected = 1, actual = repository.repositoryCalledCount)
        assertEquals(expected = 1, actual = dispatchers.dispatcherUiCalledCount)
        assertEquals(expected = 1, actual = callback.invokeCalledCount)
    }

    private class Callback : (List<String>) -> Unit {
        var invokeCalledCount = 0

        override fun invoke(p1: List<String>) {
            invokeCalledCount++
        }
    }

    private class FakeDispatchers : DispatchersList {
        val dispatcher = StandardTestDispatcher()

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