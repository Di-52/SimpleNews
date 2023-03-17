package domain.mappers

import NewsListDomain
import domain.models.DomainError
import presentation.models.ResultUi

/**
 * @author Demitrist on 12.02.2023
 **/

interface ResultDomainMapper {

    fun map(data: NewsListDomain): ResultUi

    fun map(error: DomainError): ResultUi
}