package data

import NewsListDomain
import data.models.NewsListData
import domain.models.DomainError
import domain.models.ResultDomain
import kotlinx.coroutines.TimeoutCancellationException
import okhttp3.internal.http2.ConnectionShutdownException
import okio.IOException

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultDataToDomainMapper {

    fun map(data: NewsListData): ResultDomain
    fun map(e: Exception): ResultDomain

    class Base(private val newsMapper:NewsListData.Mapper<NewsListDomain>) : ResultDataToDomainMapper {

        override fun map(data: NewsListData): ResultDomain {
            val result = data.map(newsMapper)
            return if (result.haveNoNews())
                ResultDomain.Fail(error = DomainError.NoResult(location = result.location()))
            else
                ResultDomain.Success(news = result)
        }

        override fun map(e: Exception): ResultDomain {
            val message = when (e) {
                is NoDataException -> DomainError.GenericError(e.message?:"NoDataException")
                is TimeoutCancellationException -> DomainError.ConnectionError(message = "Timed out of response")
                is IllegalStateException -> DomainError.GenericError("Something went wrong")
                is ConnectionShutdownException -> DomainError.ConnectionError(message = "Server closed connection")
                is IOException -> DomainError.ConnectionError(message = "Server is unreachable")
                else -> DomainError.ConnectionError(message = "Server is unreachable")
            }
            return ResultDomain.Fail(message)
        }
    }
}