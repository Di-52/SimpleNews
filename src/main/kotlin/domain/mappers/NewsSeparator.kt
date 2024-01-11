package domain.mappers

import NewsItemDomain
import NewsListDomain

/**
 * @author Demitrist on 10.03.2023
 **/

class NewsSeparator(
    private val filters: List<NewsItemDomainMapper<Boolean>>,
) : NewsListDomainMapper<NewsListDomain> {

    override fun map(
        name: String,
        location: String,
        news: List<NewsItemDomain>,
        keywords: List<String>
    ): NewsListDomain {
        val filteredNews = mutableListOf<NewsItemDomain>()
        news.forEach { newsItem ->
            var isMatch = true
            filters.forEach { filter ->
                isMatch = isMatch && newsItem.map(filter)
            }
            if (isMatch) filteredNews.add(newsItem)
        }
        return NewsListDomain(
            name = name,
            location = location,
            news = filteredNews,
            keywords = keywords
        )
    }
}