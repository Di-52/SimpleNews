package domain.mappers

import NewsItemDomain
import NewsListDomain

/**
 * @author Demitrist on 10.03.2023
 **/

class NewsSorter(private val sortOrderDesc: Boolean) : NewsListDomainMapper<NewsListDomain> {

    override fun map(
        name: String, location: String, news: List<NewsItemDomain>, keywords: List<String>
    ): NewsListDomain {
        val sortedNews = news.toMutableList()
        val expected = if (sortOrderDesc) -1 else 1
        var ret = 1
        while (ret > 0) {
            ret = 0
            for (i in 0 until sortedNews.size - 1) {
                if (sortedNews[i].map(NewsItemCompareDate(sortedNews[i + 1])) == expected) {
                    ret++
                    val temp = sortedNews[i]
                    sortedNews[i] = sortedNews[i + 1]
                    sortedNews[i + 1] = temp
                }
            }
        }
        return NewsListDomain(
            name = name, location = location, news = sortedNews, keywords = keywords
        )
    }
}