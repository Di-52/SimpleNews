package domain.mappers

import NewsItemDomain

/**
 * @author Demitrist on 10.03.2023
 **/

interface NewsListDomainMapper<T> {

    fun map(
        name: String,
        location: String,
        news: List<NewsItemDomain>,
        keywords: List<String>
    ): T
}