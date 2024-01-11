package data.models

import NewsItemDomain
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Demitrist on 09.03.2023
 **/

class NewsItemDataToDomainMapper {

    fun map(
        id: Int,
        title: String,
        description: String,
        date: String,
        keywords: List<String>,
        visible: Boolean
    ): NewsItemDomain {
        val dateTime = LocalDateTime.parse(
            date,
            DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm:ss xxxx")
        )
        return NewsItemDomain(id, title, description, dateTime, keywords, visible)
    }
}