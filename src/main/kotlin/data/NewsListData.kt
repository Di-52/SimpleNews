package data

import Abstract
import NewsList
import ServerResponseToNewsListMapper

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsListData(
    private val name: String,
    private val location: String,
    private val news: List<NewsItemData>,
) : Abstract.MappableObject<NewsList, ServerResponseToNewsListMapper> {
    override fun map(mapper: ServerResponseToNewsListMapper) = mapper.map(name, location, news)
}