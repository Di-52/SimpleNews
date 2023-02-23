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

    interface Mapper<T> {
        fun map(name: String, location: String, news: List<NewsItemDomain>, keywords: List<String>): T

        class Filter(
            private val filters: List<NewsItemDomain.Mapper<Boolean>>,
        ) : Mapper<NewsListDomain> {
            override fun map(
                name: String,
                location: String,
                news: List<NewsItemDomain>,
                keywords: List<String>
            ): NewsListDomain {
                val filteredNews = mutableListOf<NewsItemDomain>()
                news.forEach { newsItem ->
                    var isMatch = true
                    filters.forEach { filter ->
                        isMatch = isMatch && newsItem.map(filter)
                    }
                    if (isMatch) filteredNews.add(newsItem)
                }
                return NewsListDomain(name = name, location = location, news = filteredNews, keywords = keywords)
            }
        }

        class Sorter(private val sortOrderDesc: Boolean) :
            Mapper<NewsListDomain> {
            override fun map(
                name: String,
                location: String,
                news: List<NewsItemDomain>,
                keywords: List<String>
            ): NewsListDomain {
                val sortedNews = news.toMutableList()
                val expected = if (sortOrderDesc) -1 else 1
                var ret = 1
                while (ret > 0) {
                    ret = 0
                    for (i in 0 until sortedNews.size - 1) {
                        if (sortedNews[i].map(NewsItemDomain.Mapper.CompareDate(sortedNews[i + 1])) == expected) {
                            ret++
                            val temp = sortedNews[i]
                            sortedNews[i] = sortedNews[i + 1]
                            sortedNews[i + 1] = temp
                        }
                    }
                }
                return NewsListDomain(name = name, location = location, news = sortedNews, keywords = keywords)
            }

        }

        class ToString(private val itemMapper: NewsItemDomain.Mapper<String>) : Mapper<String> {
            override fun map(
                name: String,
                location: String,
                news: List<NewsItemDomain>,
                keywords: List<String>
            ): String {
                var result = ""
                if (news.size > 0) {
                    result += "\n\n$name from $location: \n\n"
                    news.forEach { result += "${it.map(itemMapper)} \n" }
                } else
                    result += "In $location hasn't any news..."
                return result
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(name = name, location = location, news = news, keywords = keywords)
}