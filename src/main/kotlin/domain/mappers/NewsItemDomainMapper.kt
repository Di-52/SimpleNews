package domain.mappers

import java.time.LocalDateTime

/**
 * @author Demitrist on 10.03.2023
 **/

interface NewsItemDomainMapper<T> {

    fun map(
        id: Int,
        title: String,
        description: String,
        date: LocalDateTime,
        keywords: List<String>,
        visible: Boolean
    ): T
}