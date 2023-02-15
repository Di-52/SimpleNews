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
        ) : Mapper<NewsListDomain> {

            override fun map(name: String, location: String, news: List<NewsItemData>): NewsListDomain {
                val list = mutableListOf<NewsItemDomain>()
                val keys = arrayListOf<String>()
                news.forEach { newsItem ->
                    val item = newsItem.map(itemMapper)
                    list.add(item)
                    item.keywords(keys)
                }
                return NewsListDomain(location = location, name = name, news = list.toList(), keywords = keys)
            }
        }
    }

    fun <T> map(mapper: Mapper<T>): T = mapper.map(name = name, location = location, news = news)
}