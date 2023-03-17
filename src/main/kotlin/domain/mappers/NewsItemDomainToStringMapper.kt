package domain.mappers

import java.time.LocalDateTime

/**
 * @author Demitrist on 10.03.2023
 **/

class NewsItemDomainToStringMapper : NewsItemDomainMapper<String> {

    override fun map(
        id: Int,
        title: String,
        description: String,
        date: LocalDateTime,
        keywords: List<String>,
        visible: Boolean
    ): String {
        val dateString = "" + date.dayOfMonth + " " + date.month.toString()
            .lowercase() + " " + date.year + " at " + date.hour + ":" + if (date.minute < 10) "00" else "" + date.minute
        var result = ""
        result += "[$id] "
        result += " $dateString"
        result += "\n${title.uppercase()}\n"
        result += "\n"
        result += "\t$description\n"
        result += "\n"
        if (keywords.size > 0) {
            result += "Keys: "
            keywords.forEach { result += "[$it] " }
            result += "\n\n-------------------------------"
        }
        result += "\n"
        return result
    }
}