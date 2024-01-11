package domain.mappers

import NewsItemDomain

/**
 * @author Demitrist on 10.03.2023
 **/

class NewsListToStringMapper(private val itemMapper: NewsItemDomainMapper<String>) :
    NewsListDomainMapper<String> {

    override fun map(
        name: String,
        location: String,
        news: List<NewsItemDomain>,
        keywords: List<String>
    ): String {
        var result = ""
        if (news.size > 0) {
            result += "\n\n$name from $location: \n\n"
            news.forEach { result += "${it.map(itemMapper)} \n" }
        } else
            result += "In $location hasn't any news..."
        return result
    }
}