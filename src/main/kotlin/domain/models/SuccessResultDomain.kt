package domain.models

import NewsListDomain
import domain.mappers.ResultDomainMapper
import domain.mappers.NewsSeparator
import domain.mappers.NewsSorter

/**
 * @author Demitrist on 10.03.2023
 **/

data class SuccessResultDomain(private val news: NewsListDomain) : ResultDomain {

    override fun map(resultMapper: ResultDomainMapper) = resultMapper.map(news)

    override fun filteredNews(mapper: NewsSeparator) =
        SuccessResultDomain(news = news.map(mapper = mapper))

    override fun sortedNews(mapper: NewsSorter) =
        SuccessResultDomain(news = news.map(mapper = mapper))

    override fun keywords() = news.keywords()
}
