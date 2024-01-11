package domain.models

import domain.mappers.ResultDomainMapper
import domain.mappers.NewsSeparator
import domain.mappers.NewsSorter
import presentation.models.ResultUi

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultDomain {

    fun map(resultMapper: ResultDomainMapper): ResultUi

    fun filteredNews(mapper: NewsSeparator): ResultDomain

    fun sortedNews(mapper: NewsSorter): ResultDomain

    fun keywords(): List<String>
}

