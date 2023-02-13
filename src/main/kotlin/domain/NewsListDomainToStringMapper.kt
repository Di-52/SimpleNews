package domain

import NewsItemDomain
import core.Mapper

/**
 * @author Demitrist on 11.02.2023
 **/

interface NewsListDomainToStringMapper : Mapper {

    fun map(location: String, name: String, news: List<NewsItemDomain>): String

    class Base(private val itemMapper: NewsItemDomainToStringMapper) : NewsListDomainToStringMapper {
        override fun map(location: String, name: String, news: List<NewsItemDomain>): String {
            var result = ""
            if (news.size > 0) {
                result += "News from $location...\n\n"
                result += "$name \n\n"
                news.forEach { result += "${it.map(itemMapper)} \n" }
            } else
                result += "In $location hasn't any news..."
            return result
        }
    }
}