package data.models

import NewsListDomain
import core.MappableObject
import data.NewsListDataToDomainMapper

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsListData(
    private val name: String,
    private val location: String,
    private val news: List<NewsItemData>,
) : MappableObject<NewsListDomain, NewsListDataToDomainMapper> {

    override fun map(mapper: NewsListDataToDomainMapper) = mapper.map(name, location, news)
}