package domain.mappers

import NewsItemDomain
import java.time.LocalDateTime

/**
 * @author Demitrist on 10.03.2023
 **/

class NewsItemCompareDate(private val other: NewsItemDomain) : NewsItemDomainMapper<Int> {

    override fun map(
        id: Int,
        title: String,
        description: String,
        date: LocalDateTime,
        keywords: List<String>,
        visible: Boolean
    ) = other.compareByDate(date)
}