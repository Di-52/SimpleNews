package data.models

import NewsItemDomain

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
) {

    fun map(mapper: NewsItemDataToDomainMapper): NewsItemDomain = mapper.map(
        id = id,
        title = title,
        description = description,
        date = date,
        keywords = keywords,
        visible = visible
    )
}