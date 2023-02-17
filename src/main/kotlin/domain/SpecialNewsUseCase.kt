package domain

import NewsItemDomain
import NewsListDomain
import core.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Demitrist on 14.02.2023
 **/

interface SpecialNewsUseCase {

    fun news()
    fun sort(orderDesc: Boolean)
    fun keyword(key: String)

    class Base(
        private val repository: NewsRepository,
        private val dispatchers: DispatchersList,
        private val uiMapper: ResultDomainToUiMapper,
        private val output: (String) -> Unit
    ) : SpecialNewsUseCase {
        private val keys = mutableListOf<String>()
        private var sortOrderDesc = true

        override fun news() {
            val matchers = listOf<NewsItemDomain.Mapper<Boolean>>(
                NewsItemDomain.Mapper.MatchAnyKeyword(keys = keys.toList()),
                NewsItemDomain.Mapper.MatchVisibility(isVisible = true)
            )
            val filter = NewsListDomain.Mapper.Filter(filters = matchers)
            val sorter = NewsListDomain.Mapper.Sorter(sortOrderDesc = sortOrderDesc)
            CoroutineScope(dispatchers.io()).launch {
                var news = repository.fetchNews()
                news = news.sortedNews(mapper = sorter)
                news = news.filteredNews(mapper = filter)
                val result = news.map(resultMapper = uiMapper)
                withContext(dispatchers.ui()) {
                    output.invoke(result.map())
                }
            }
        }

        override fun sort(orderDesc: Boolean) {
            sortOrderDesc = orderDesc
            output.invoke("\nSort by " + if (sortOrderDesc) "DESC" else "ASC" + "\n")
        }

        override fun keyword(key: String) {
            val action: String
            if (keys.contains(key)) {
                keys.remove(key)
                action = "removed"
            } else {
                keys.add(key)
                action = "added"
            }
            output.invoke("\nKey [$key] $action.\n")
        }
    }
}