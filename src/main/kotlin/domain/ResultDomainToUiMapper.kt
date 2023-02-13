package domain

import NewsListDomain
import domain.models.DomainError
import presentation.models.ResultUI

/**
 * @author Demitrist on 12.02.2023
 **/

interface ResultDomainToUiMapper {

    fun map(data:NewsListDomain): ResultUI
    fun map(error: DomainError): ResultUI

    class Base(private val mapper: NewsListDomainToStringMapper) : ResultDomainToUiMapper{
        override fun map(data: NewsListDomain): ResultUI {
            TODO("Not yet implemented")
        }


        override fun map(error: DomainError): ResultUI {
            val result = when(error){
                is DomainError.NoResult -> "Nothing new in ${error.location} at this time."
                is DomainError.ConnectionError -> "Can't load news, cause: ${error.message}. Check you internet connection."
                else -> "Something went wrong."
            }
            return ResultUI.Fail(message = result)
        }

    }
}