import domain.mappers.NewsListDomainMapper
import domain.models.Content

/**
 * @author Demitrist on 10.02.2023
 **/

data class NewsListDomain(
    private val name: String,
    private val location: String,
    private val news: List<NewsItemDomain>,
    private val keywords: List<String>
) : Content {

    override fun haveNoNews() = news.isEmpty()

    override fun location() = location

    override fun keywords() = keywords

    fun <T> map(mapper: NewsListDomainMapper<T>): T =
        mapper.map(name = name, location = location, news = news, keywords = keywords)
}