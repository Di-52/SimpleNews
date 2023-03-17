package data

import data.models.ResultData
import domain.NewsRepository
import domain.models.ResultDomain

/**
 * @author Demitrist on 13.02.2023
 **/

class UsualNewsRepository(
    private val cloud: DataSource,
    private val cache: MutableDataSource,
    private val resultMapper: ResultDataToDomainMapper
) : NewsRepository {

    override suspend fun fetchNews(): ResultDomain {
        val data: ResultData
        if (cache.haveData()) {
            data = cache.fetchData()
        } else {
            data = cloud.fetchData()
            if (data.isSuccess()) {
                cache.save(data)
            }
        }
        return data.map(mapper = resultMapper)
    }
}