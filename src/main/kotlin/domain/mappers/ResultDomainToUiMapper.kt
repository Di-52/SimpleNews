package domain.mappers

import NewsListDomain
import domain.models.errors.ConnectionDomainError
import domain.models.errors.DomainError
import presentation.StringFormatter
import presentation.models.ResultUI

/**
 * @author Demitrist on 10.03.2023
 **/

class ResultDomainToUiMapper(
    private val listMapper: NewsListDomainMapper<String>,
    private val formatter: StringFormatter
) : ResultDomainMapper {

    override fun map(data: NewsListDomain) = ResultUI(text = formatter.format(data.map(listMapper)))

    override fun map(error: DomainError): ResultUI {
        val result = when (error) {
            is ConnectionDomainError -> "Can't load news, cause: ${error.message}. Check you internet connection."
            else -> "Something went wrong."
        }
        return ResultUI(text = formatter.format(result))
    }
}