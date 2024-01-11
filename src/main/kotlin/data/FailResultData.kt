package data

import data.models.ResultData

/**
 * @author Demitrist on 10.03.2023
 **/

data class FailResultData(private val e: Exception) : ResultData {

    override fun map(mapper: ResultDataToDomainMapper) = mapper.map(e)

    override fun isSuccess() = false
}