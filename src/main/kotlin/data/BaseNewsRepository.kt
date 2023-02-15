package data

import domain.NewsRepository

/**
 * @author Demitrist on 13.02.2023
 **/

class BaseNewsRepository(
    private val dataSource: DataSource,
    private val mapper: ResultDataToDomainMapper
) : NewsRepository {

    override suspend fun fetchNews() = dataSource.fetchData().map(mapper)
}