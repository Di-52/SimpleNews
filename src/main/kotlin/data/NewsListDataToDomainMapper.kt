package data

import NewsItemDomain
import NewsListDomain
import core.Mapper
import data.models.NewsItemData

/**
 * @author Demitrist on 10.02.2023
 **/

interface NewsListDataToDomainMapper : Mapper {

    fun map(name: String, location: String, news: List<NewsItemData>): NewsListDomain

    class Base(private val itemMapper: NewsItemDataToDomainMapper) : NewsListDataToDomainMapper {

        override fun map(name: String, location: String, news: List<NewsItemData>): NewsListDomain {
            val list = mutableListOf<NewsItemDomain>()
            val keys = arrayListOf<String>()
            news.forEach {
                val item = it.map(itemMapper)
                list.add(item)
                item.provideKeywords(keys)
            }
            return NewsListDomain(location, name, list.toList(), keys)
        }
    }
}