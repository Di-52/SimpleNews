package data.models

import NewsItemDomain
import NewsListDomain

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsListData(
    private val name: String,
    private val location: String,
    private val news: List<NewsItemData>,
) {

    interface Mapper<T> {

        fun map(name: String, location: String, news: List<NewsItemData>): T

        class ToDomain(
            private val itemMapper: NewsItemData.Mapper<NewsItemDomain>,
            private val matchers: List<NewsItemData.Mapper<Boolean>>
        ) : Mapper<NewsListDomain> {

            override fun map(name: String, location: String, news: List<NewsItemData>): NewsListDomain {
                val list = mutableListOf<NewsItemDomain>()
                val keys = arrayListOf<String>()
                news.forEach { newsItem ->
                    var isMatch = true
                    matchers.forEach {
                        isMatch = isMatch && newsItem.map(it)
                    }
                    val item = newsItem.map(itemMapper)
                    if (isMatch) list.add(item)
                    item.keywords(keys)
                }
                return NewsListDomain(location, name, list.toList(), keys)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(name = name, location = location, news = news)
}