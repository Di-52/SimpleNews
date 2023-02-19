package domain

import core.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Demitrist on 14.02.2023
 **/

interface KeywordsUseCase {

    fun keywords(block: (List<String>) -> Unit)

    class Base(
        private val repository: NewsRepository,
        private val dispatchers: DispatchersList
    ) : KeywordsUseCase {
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
}