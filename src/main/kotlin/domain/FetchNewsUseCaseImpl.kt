package domain

import core.DispatchersList
import domain.mappers.ResultDomainMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Demitrist on 10.03.2023
 **/

class FetchNewsUseCaseImpl(
    private val repository: NewsRepository,
    private val dispatchers: DispatchersList,
    private val uiMapper: ResultDomainMapper,
    private val output: (String) -> Unit
) : FetchNewsUseCase {

    override fun news() {
        CoroutineScope(dispatchers.io()).launch {
            val news = repository.fetchNews()
            val result = news.map(resultMapper = uiMapper)
            withContext(dispatchers.ui()){
                output.invoke(result.map())
            }
        }
    }
}