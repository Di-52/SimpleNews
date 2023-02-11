/**
 * @author Demitrist on 10.02.2023
 **/

class NewsList(
    private val location: String,
    private val name: String,
    private val news: List<NewsItem>,
    private val keywords: List<String>
) : Abstract.MappableObject<String, NewsListToStringMapper>, Filterable, Sortable {
    private val keys = arrayListOf<String>()
    private val sorting = arrayListOf<SortOrder>()

    fun keywords() = keywords
    override fun map(mapper: NewsListToStringMapper): String {
        var actualNews = news.toMutableList()
        if (keys.isNotEmpty()) {
            news.forEach { newsItem ->
                var comp = true
                keys.forEach { key ->
                    comp = comp && newsItem.checkKeyword(key = key)
                }
                if (!comp) actualNews.remove(newsItem)
            }
        }
        if (sorting.isNotEmpty())
            when (sorting[0]) {
                SortOrder.DATE_ASK -> actualNews.sortBy { it.provideDate() }
                SortOrder.DATE_DESC -> actualNews.sortByDescending { it.provideDate() }
            }
        return mapper.map(location, name, actualNews)
    }

    override fun byKeyword(key: String) {
        if (key.isEmpty()) keys.clear()
        else keys.add(key)
    }

    override fun sort(order: SortOrder) {
        sorting.clear()
        sorting.add(order)
    }


}

interface Filterable {
    fun byKeyword(key: String)
}

interface Sortable {
    fun sort(order: SortOrder)
}

enum class SortOrder { DATE_ASK, DATE_DESC }