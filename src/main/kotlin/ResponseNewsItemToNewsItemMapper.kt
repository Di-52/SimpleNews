import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Demitrist on 10.02.2023
 **/

interface ResponseNewsItemToNewsItemMapper : Abstract.Mapper {

    fun map(
        id: Int,
        title: String,
        description: String,
        date: String,
        keywords: List<String>,
        visible: Boolean
    ): NewsItem

    //
    class Base : ResponseNewsItemToNewsItemMapper {
        override fun map(
            id: Int,
            title: String,
            description: String,
            date: String,
            keywords: List<String>,
            visible: Boolean
        ): NewsItem {
            val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm:ss xxxx"))
            return NewsItem(id, title, description, dateTime, keywords, visible)
        }
    }
}