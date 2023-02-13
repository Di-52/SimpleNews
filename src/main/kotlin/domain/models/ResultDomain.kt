package domain.models

import NewsListDomain

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultDomain  {

    class Success(private val result: NewsListDomain) : ResultDomain
    class Fail(private val error: DomainError) : ResultDomain
}

