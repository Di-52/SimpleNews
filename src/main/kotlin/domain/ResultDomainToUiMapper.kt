package domain

import NewsListDomain
import domain.models.DomainError
import presentation.Formatter
import presentation.models.ResultUi

/**
 * @author Demitrist on 12.02.2023
 **/

interface ResultDomainToUiMapper {

    fun map(data: NewsListDomain): ResultUi
    fun map(error: DomainError): ResultUi

    class Base(private val listMapper: NewsListDomain.Mapper<String>, private val formatter: Formatter<String,String>) : ResultDomainToUiMapper {

        override fun map(data: NewsListDomain) = ResultUi(text = formatter.format(data.map(listMapper)))

        override fun map(error: DomainError): ResultUi {
            val result = when (error) {
                is DomainError.NoResult -> "Nothing new in ${error.location} at this time."
                is DomainError.ConnectionError -> "Can't load news, cause: ${error.message}. Check you internet connection."
                else -> "Something went wrong."
            }
            return ResultUi(text = formatter.format(result))
        }
    }
}