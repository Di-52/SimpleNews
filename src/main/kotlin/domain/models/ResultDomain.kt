package domain.models

import NewsListDomain
import domain.ResultDomainToUiMapper
import presentation.models.ResultUi

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultDomain {

    fun map(resultMapper: ResultDomainToUiMapper): ResultUi
    fun filteredNews(mapper: NewsListDomain.Mapper.Filter): ResultDomain
    fun sortedNews(mapper: NewsListDomain.Mapper.Sorter): ResultDomain

    data class Success(private val news: NewsListDomain) : ResultDomain {

        override fun map(resultMapper: ResultDomainToUiMapper) = resultMapper.map(news)

        override fun filteredNews(mapper: NewsListDomain.Mapper.Filter): ResultDomain {
            val filteredNews = news.map(mapper)
            return if (filteredNews.haveNoNews()) Fail(error = DomainError.NoResult(location = filteredNews.location()))
            else Success(filteredNews)
        }

        override fun sortedNews(mapper: NewsListDomain.Mapper.Sorter): ResultDomain {
            val sortedNews = news.map(mapper)
            return Success(sortedNews)
        }
    }

    data class Fail(private val error: DomainError) : ResultDomain {

        override fun map(resultMapper: ResultDomainToUiMapper) = resultMapper.map(error)
        override fun filteredNews(mapper: NewsListDomain.Mapper.Filter) = Fail(error)
        override fun sortedNews(mapper: NewsListDomain.Mapper.Sorter) = Fail(error)
    }


}

