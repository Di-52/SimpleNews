package domain

import core.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Demitrist on 10.03.2023
 **/

class FetchKeywordsUseCaseImpl(
    private val repository: NewsRepository,
    private val dispatchers: DispatchersList
) : FetchKeywordsUseCase {

    override fun keywords(block: (List<String>) -> Unit) {
        CoroutineScope(dispatchers.io()).launch {
            val news = repository.fetchNews()
            val result = news.keywords()
            withContext(dispatchers.ui()) {
                block.invoke(result)
            }
        }
    }
}