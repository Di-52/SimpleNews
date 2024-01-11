package domain.mappers

import NewsListDomain
import domain.models.ConnectionDomainError
import domain.models.DomainError
import presentation.StringFormatter
import presentation.models.ResultUi

/**
 * @author Demitrist on 10.03.2023
 **/

class ResultDomainToUiMapper(
    private val listMapper: NewsListDomainMapper<String>,
    private val formatter: StringFormatter
) : ResultDomainMapper {

    override fun map(data: NewsListDomain) = ResultUi(text = formatter.format(data.map(listMapper)))

    override fun map(error: DomainError): ResultUi {
        val result = when (error) {
            is ConnectionDomainError -> "Can't load news, cause: ${error.message}. Check you internet connection."
            else -> "Something went wrong."
        }
        return ResultUi(text = formatter.format(result))
    }
}