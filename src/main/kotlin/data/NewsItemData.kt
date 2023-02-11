package data

import Abstract
import NewsItem
import ResponseNewsItemToNewsItemMapper

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsItemData(
    private val id: Int = 0,
    private val title: String = "",
    private val description: String = "",
    private val date: String = "",
    private val keywords: List<String> = emptyList(),
    private val visible: Boolean = true
) : Abstract.MappableObject<NewsItem, ResponseNewsItemToNewsItemMapper> {

    override fun map(mapper: ResponseNewsItemToNewsItemMapper): NewsItem =mapper.map(id,title,description,date,keywords,visible)
}