package domain

import NewsListDomain
import data.ResultDataToDomainMapper
import data.models.NewsItemData
import data.models.NewsListData
import domain.models.ResultDomain

/**
 * @author Demitrist on 13.02.2023
 **/

class NewsUseCase(private val repository: NewsRepository) {
    private val keys = mutableListOf<String>()
    private var sortOrderDesc: Boolean = true
    private val matchers = mutableListOf<NewsItemData.Mapper<Boolean>>()

    fun filter(key: String) {
        if (keys.contains(key)) keys.remove(key)
        else keys.add(key)
    }

    fun sort(orderDesc: Boolean) {
        sortOrderDesc = orderDesc
    }

    fun news(newsHandler: (String) -> Unit): String {
        //matchers.add(NewsItemData.Mapper.MatchAllKeywords(keys = keys))
        //matchers.add(NewsItemData.Mapper.MatchVisibility(isVisible = true))

        var result : ResultDomain

        return ""
    }
}