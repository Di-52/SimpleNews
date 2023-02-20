package data

import data.models.ResultData
import domain.NewsRepository
import domain.models.ResultDomain

/**
 * @author Demitrist on 13.02.2023
 **/

class BaseNewsRepository(
    private val cloud: DataSource,
    private val cache: DataSource.Mutable,
    private val mapper: ResultDataToDomainMapper
) : NewsRepository {

    override suspend fun fetchNews(): ResultDomain {
        val result: ResultData
        if (cache.haveData()){
            result = cache.fetchData()
        }
        else
        {
            result = cloud.fetchData()
            if (result.isSuccess()){
                cache.save(result)
            }
        }
        return result.map(mapper = mapper)
    }
}