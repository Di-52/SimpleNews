import domain.ProvideKeywords
import domain.mappers.NewsItemDomainMapper
import java.time.LocalDateTime

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsItemDomain(
    private val id: Int,
    private val title: String,
    private val description: String,
    private val date: LocalDateTime,
    private val keywords: List<String>,
    private val visible: Boolean
) : ProvideKeywords {

    override fun keywords(list: ArrayList<String>) {
        keywords.forEach { key ->
            list.add(key)
        }
    }

    fun compareByDate(other: LocalDateTime) = date.compareTo(other)

    fun <T> map(mapper: NewsItemDomainMapper<T>): T =
        mapper.map(id, title, description, date, keywords, visible)
}