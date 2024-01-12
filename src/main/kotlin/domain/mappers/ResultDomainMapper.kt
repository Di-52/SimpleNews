package domain.mappers

import NewsListDomain
import domain.models.errors.DomainError
import presentation.models.ResultUI

/**
 * @author Demitrist on 12.02.2023
 **/

interface ResultDomainMapper {

    fun map(data: NewsListDomain): ResultUI

    fun map(error: DomainError): ResultUI
}