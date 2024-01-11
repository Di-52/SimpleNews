package data.models

import NewsListDomain

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsListData(
    private val name: String,
    private val location: String,
    private val news: List<NewsItemData>,
) {

    fun map(mapper: NewsListDataToDomainMapper): NewsListDomain =
        mapper.map(name = name, location = location, news = news)
}