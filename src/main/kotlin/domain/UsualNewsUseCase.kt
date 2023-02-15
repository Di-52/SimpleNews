package domain

import core.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Demitrist on 13.02.2023
 **/

interface UsualNewsUseCase {

    fun news()

    class Base(
        private val repository: NewsRepository,
        private val dispatchers: DispatchersList,
        private val uiMapper: ResultDomainToUiMapper,
        private val output: (String) -> Unit
    ) : UsualNewsUseCase {

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
}