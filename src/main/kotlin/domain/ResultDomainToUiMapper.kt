package domain

import NewsListDomain
import domain.models.DomainError
import presentation.models.ResultUi

/**
 * @author Demitrist on 12.02.2023
 **/

interface ResultDomainToUiMapper {

    fun map(data: NewsListDomain): ResultUi
    fun map(error: DomainError): ResultUi

    class Base(private val listMapper: NewsListDomain.Mapper<String>) : ResultDomainToUiMapper {

        override fun map(data: NewsListDomain) = ResultUi(text = data.map(listMapper))

        override fun map(error: DomainError): ResultUi {
            val result = when (error) {
                is DomainError.NoResult -> "Nothing new in ${error.location} at this time."
                is DomainError.ConnectionError -> "Can't load news, cause: ${error.message}. Check you internet connection."
                else -> "Something went wrong."
            }
            return ResultUi(text = result)
        }
    }
}