package data.models

import NewsItemDomain
import core.MappableObject
import data.NewsItemDataToDomainMapper
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Demitrist on 10.02.2023
 **/

class NewsItemData(
    private val id: Int = 0,
    private val title: String = "",
    private val description: String = "",
    private val date: String = "",
    private val keywords: List<String> = emptyList(),
    private val visible: Boolean = true
) : MappableObject<NewsItemDomain, NewsItemDataToDomainMapper> {
//todo replace Mappable with ToDomain
    override fun map(mapper: NewsItemDataToDomainMapper): NewsItemDomain =
        mapper.map(id, title, description, date, keywords, visible)
//
//    interface Mapper<T> {
//        fun map(id: Int, title: String, description: String, date: String, keywords: List<String>, visible: Boolean): T
//
//        class MatchAllKeywords(private val keys: List<String>) : Mapper<Boolean> {
//            override fun map(
//                id: Int,
//                title: String,
//                description: String,
//                date: String,
//                keywords: List<String>,
//                visible: Boolean
//            ) = keywords.containsAll(keys)
//        }
//
//        class MatchAnyKeyword(private val keys: List<String>) : Mapper<Boolean> {
//            override fun map(
//                id: Int,
//                title: String,
//                description: String,
//                date: String,
//                keywords: List<String>,
//                visible: Boolean
//            ): Boolean {
//                var result = false
//                keys.forEach { result = result || keywords.contains(it) }
//                return result
//            }
//        }
//
//        class MatchVisibility(private val isVisible: Boolean = true) : Mapper<Boolean> {
//            override fun map(
//                id: Int,
//                title: String,
//                description: String,
//                date: String,
//                keywords: List<String>,
//                visible: Boolean
//            ) = visible == isVisible
//        }
//
//        class ToDomain : Mapper<NewsItemDomain> {
//            override fun map(
//                id: Int,
//                title: String,
//                description: String,
//                date: String,
//                keywords: List<String>,
//                visible: Boolean
//            ): NewsItemDomain {
//                val dateTime = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("uuuu-MM-dd kk:mm:ss xxxx"))
//                return NewsItemDomain(id, title, description, dateTime, keywords, visible)
//            }
//        }
//
//    }
//
//    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, title, description, date, keywords, visible)
}