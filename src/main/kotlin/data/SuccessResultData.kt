package data

import data.models.NewsListData
import data.models.ResultData

/**
 * @author Demitrist on 10.03.2023
 **/

data class SuccessResultData(private val data: NewsListData) : ResultData {

    override fun map(mapper: ResultDataToDomainMapper) = mapper.map(data)

    override fun isSuccess() = true
}