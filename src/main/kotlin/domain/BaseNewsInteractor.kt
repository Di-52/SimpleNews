package domain

import NewsItemDomain
import NewsListDomain
import core.DispatchersList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Demitrist on 13.02.2023
 **/

class BaseNewsInteractor(private val repository: NewsRepository, private val dispatchersList: DispatchersList) :
    NewsInteractor {
    private val keys = mutableListOf<String>()
    private var sortOrderDesc = true

    override fun allNews(callBack: (String) -> Unit) {
        CoroutineScope(dispatchersList.io()).launch {
            var news = repository.fetchNews()
            withContext(dispatchersList.ui()) {
                callBack.invoke(
                    news.map(
                        resultMapper = ResultDomainToUiMapper.Base(
                            listMapper = NewsListDomain.Mapper.ToString(
                                itemMapper = NewsItemDomain.Mapper.ToString())))
                        .map()
                )
            }
        }
    }

    override fun news(callBack: (String) -> Unit) {
        CoroutineScope(dispatchersList.io()).launch {
            var res = repository.fetchNews()
            withContext(dispatchersList.ui()) {
                callBack.invoke(
                    ""
                )
            }
        }
    }

    override fun filter(key: String) {
        if (keys.contains(key))
            keys.remove(key)
        else
            keys.add(key)
    }

    override fun sort(orderDesc: Boolean) {
        sortOrderDesc = orderDesc
    }
}