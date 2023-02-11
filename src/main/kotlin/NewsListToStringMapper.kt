/**
 * @author Demitrist on 11.02.2023
 **/

interface NewsListToStringMapper : Abstract.Mapper {

    fun map(location: String, name: String, news: List<NewsItem>): String

    class Base(private val itemMapper: NewsItemToStringMapper) : NewsListToStringMapper {
        override fun map(location: String, name: String, news: List<NewsItem>): String {
            var result = ""
            if (news.size > 0) {
                result += "News from $location...\n\n"
                result += "$name \n\n"
                news.forEach { result += "${it.map(itemMapper)} \n" }
            } else
                result += "In $location hasn't news..."
            return result
        }
    }
}