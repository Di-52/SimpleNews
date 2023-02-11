import data.NewsItemData

/**
 * @author Demitrist on 10.02.2023
 **/

interface ServerResponseToNewsListMapper :Abstract.Mapper {

    fun map(name: String, location: String, news: List<NewsItemData>): NewsList

    class Base(private val itemMapper: ResponseNewsItemToNewsItemMapper) : ServerResponseToNewsListMapper {

        override fun map(name: String, location: String, news: List<NewsItemData>): NewsList {
            val list = mutableListOf<NewsItem>()
            val keys = arrayListOf<String>()
            news.forEach {
                val item = it.map(itemMapper)
                list.add(item)
                item.provideKeywords(keys)
            }
            return NewsList(location, name, list.toList(), keys)
        }
    }
}