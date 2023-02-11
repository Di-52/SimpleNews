import java.time.LocalDateTime

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsItem(
    private val id: Int,
    private val title: String,
    private val description: String,
    private val date: LocalDateTime,
    private val keywords: List<String>,
    private val visible: Boolean
) : Abstract.MappableObject<String,NewsItemToStringMapper>, KeywordProvider, KeywordChecker, DateProvider, VisibilityProvider {

    override fun provideKeywords(list: ArrayList<String>) {
        keywords.forEach { key ->
            list.add(key)
        }
    }

    override fun checkKeyword(key: String) = keywords.contains(key)
    override fun provideDate() = date
    override fun isVisible() = visible
    override fun map(mapper: NewsItemToStringMapper) = mapper.map(id,title,description,date,keywords)
}


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