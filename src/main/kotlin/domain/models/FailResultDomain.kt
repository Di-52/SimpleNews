package domain.models

import domain.mappers.ResultDomainMapper
import domain.mappers.NewsSeparator
import domain.mappers.NewsSorter
import domain.models.errors.DomainError

/**
 * @author Demitrist on 10.03.2023
 **/

data class FailResultDomain(private val error: DomainError) : ResultDomain {

    override fun map(resultMapper: ResultDomainMapper) = resultMapper.map(error)

    override fun filteredNews(mapper: NewsSeparator) = FailResultDomain(error)

    override fun sortedNews(mapper: NewsSorter) = FailResultDomain(error)

    override fun keywords(): List<String> = emptyList()
}
