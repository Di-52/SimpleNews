package data.models

import data.ResultDataToDomainMapper
import domain.models.ResultDomain

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultData {

    fun map(mapper: ResultDataToDomainMapper): ResultDomain

    data class Success(private val data: NewsListData) : ResultData {
        override fun map(mapper: ResultDataToDomainMapper) = mapper.map(data)
    }

    data class Fail(private val e: Exception) : ResultData {
        override fun map(mapper: ResultDataToDomainMapper) = mapper.map(e)
    }
}