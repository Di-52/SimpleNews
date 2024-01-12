package data

import data.models.NewsListData
import data.models.NewsListDataToDomainMapper
import domain.models.errors.ConnectionDomainError
import domain.models.FailResultDomain
import domain.models.errors.GenericDomainError
import domain.models.ResultDomain
import domain.models.SuccessResultDomain
import kotlinx.coroutines.TimeoutCancellationException
import okhttp3.internal.http2.ConnectionShutdownException
import okio.IOException

/**
 * @author Demitrist on 11.02.2023
 **/

class ResultDataToDomainMapper(
    private val newsMapper: NewsListDataToDomainMapper
) {

    fun map(data: NewsListData) = SuccessResultDomain(data.map(newsMapper))

    fun map(e: Exception): ResultDomain {
        val message = when (e) {
            is NoDataException -> GenericDomainError(e.message ?: "NoDataException")
            is TimeoutCancellationException -> ConnectionDomainError(message = "Timed out of response")
            is IllegalStateException -> GenericDomainError("Something went wrong")
            is ConnectionShutdownException -> ConnectionDomainError(message = "Server closed connection")
            is IOException -> ConnectionDomainError(message = "Server is unreachable")
            else -> ConnectionDomainError(message = "Server is unreachable")
        }
        return FailResultDomain(message)
    }
}