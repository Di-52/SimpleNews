import domain.models.ProvideKeywords
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

    fun compareByDate(other: LocalDateTime) = date.compareTo(other)

    interface Mapper<T> {

        fun map(
            id: Int,
            title: String,
            description: String,
            date: LocalDateTime,
            keywords: List<String>,
            visible: Boolean
        ): T

        class CompareDate(private val other: NewsItemDomain) : NewsItemDomain.Mapper<Int> {
            override fun map(
                id: Int,
                title: String,
                description: String,
                date: LocalDateTime,
                keywords: List<String>,
                visible: Boolean
            ) = other.compareByDate(date)
        }

        class MatchAllKeywords(private val keys: List<String>) : NewsItemDomain.Mapper<Boolean> {
            override fun map(
                id: Int,
                title: String,
                description: String,
                date: LocalDateTime,
                keywords: List<String>,
                visible: Boolean
            ) = keywords.containsAll(keys)
        }

        class MatchAnyKeyword(private val keys: List<String>) : NewsItemDomain.Mapper<Boolean> {
            override fun map(
                id: Int,
                title: String,
                description: String,
                date: LocalDateTime,
                keywords: List<String>,
                visible: Boolean
            ): Boolean {
                var result = false
                keys.forEach { result = result || keywords.contains(it) }
                return result
            }
        }

        class MatchVisibility(private val isVisible: Boolean = true) : NewsItemDomain.Mapper<Boolean> {
            override fun map(
                id: Int,
                title: String,
                description: String,
                date: LocalDateTime,
                keywords: List<String>,
                visible: Boolean
            ) = visible == isVisible
        }

        class ToString : Mapper<String> {
            override fun map(
                id: Int,
                title: String,
                description: String,
                date: LocalDateTime,
                keywords: List<String>,
                visible: Boolean
            ): String {
                var dateString = "" + date.dayOfMonth + " " + date.month.toString()
                    .lowercase() + " " + date.year + " at " + date.hour + ":" + if (date.minute < 10) "00" else "" + date.minute
                var result = "$id - \n"
                result += "\t$title ($dateString)\n"
                result += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                result += description + "\n"
                result += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                if (keywords.size > 0) {
                    result += "Keys: "
                    keywords.forEach { result += "[$it] " }
                    result += "\n"
                }
                result += "\n\n"
                return result
            }

        }
    }

    override fun keywords(list: ArrayList<String>) {
        keywords.forEach { key ->
            list.add(key)
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, title, description, date, keywords, visible)
}
