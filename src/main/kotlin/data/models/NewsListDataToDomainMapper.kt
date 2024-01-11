package data.models

import NewsItemDomain
import NewsListDomain

/**
 * @author Demitrist on 09.03.2023
 **/

class NewsListDataToDomainMapper(
    private val itemMapper: NewsItemDataToDomainMapper
) {

    fun map(
        name: String,
        location: String,
        news: List<NewsItemData>
    ): NewsListDomain {
        val list = mutableListOf<NewsItemDomain>()
        val keys = arrayListOf<String>()
        news.forEach { newsItem ->
            val item = newsItem.map(itemMapper)
            list.add(item)
            item.keywords(keys)
        }
        return NewsListDomain(
            location = location,
            name = name,
            news = list.toList(),
            keywords = keys
        )
    }
}