package data

import NewsItemDomain
import core.Mapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Demitrist on 10.02.2023
 **/

interface NewsItemDataToDomainMapper : Mapper {

    fun map(
        id: Int,
        title: String,
        description: String,
        date: String,
        keywords: List<String>,
        visible: Boolean
    ): NewsItemDomain

    class Base : NewsItemDataToDomainMapper {
        override fun map(
            id: Int,
            title: String,
            description: String,
            date: String,
            keywords: List<String>,
            visible: Boolean
        ): NewsItemDomain {
            val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm:ss xxxx"))
            return NewsItemDomain(id, title, description, dateTime, keywords, visible)
        }
    }
}