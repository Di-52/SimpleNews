package domain.mappers

import java.time.LocalDateTime

/**
 * @author Demitrist on 10.03.2023
 **/

class NewsItemMatchAnyKeyword(private val keys: List<String>) : NewsItemDomainMapper<Boolean> {

    override fun map(
        id: Int,
        title: String,
        description: String,
        date: LocalDateTime,
        keywords: List<String>,
        visible: Boolean
    ): Boolean {
        if (keys.size == 0) return true
        var result = false
        keys.forEach { result = result || keywords.contains(it) }
        return result
    }
}