package domain.mappers

import java.time.LocalDateTime

/**
 * @author Demitrist on 10.03.2023
 **/

class
NewsItemMatchAllKeywords(private val keys: List<String>) : NewsItemDomainMapper<Boolean> {

    override fun map(
        id: Int,
        title: String,
        description: String,
        date: LocalDateTime,
        keywords: List<String>,
        visible: Boolean
    ) = keywords.containsAll(keys)
}