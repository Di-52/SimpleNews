import core.MappableObject
import data.models.NewsItemData
import domain.NewsItemDomainToStringMapper
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
) : MappableObject<String, NewsItemDomainToStringMapper>, DataProvider, KeywordChecker {
//
//    interface Mapper<T> {
//        fun map(
//            id: Int,
//            title: String,
//            description: String,
//            date: LocalDateTime,
//            keywords: List<String>,
//            visible: Boolean
//        ): T
//
//        class CompareDate(private val compairWith: LocalDateTime) : Mapper<Int> {
//            override fun map(
//                id: Int,
//                title: String,
//                description: String,
//                date: LocalDateTime,
//                keywords: List<String>,
//                visible: Boolean
//            ) = date.compareTo(other = compairWith)
//        }
//    }

    override fun provideKeywords(list: ArrayList<String>) {
        keywords.forEach { key ->
            list.add(key)
        }
    }

    override fun checkKeyword(key: String) = keywords.contains(key)
    override fun provideDate() = date
    override fun isVisible() = visible
    override fun map(mapper: NewsItemDomainToStringMapper) = mapper.map(id, title, description, date, keywords)
}

interface DataProvider : KeywordProvider, DateProvider, VisibilityProvider

interface KeywordProvider {
    fun provideKeywords(list: ArrayList<String>)
}

interface KeywordChecker {
    fun checkKeyword(key: String): Boolean
}

interface DateProvider {
    fun provideDate(): LocalDateTime
}

interface VisibilityProvider {
    fun isVisible(): Boolean
}