package data.models

import data.ResultDataToDomainMapper
import domain.models.ResultDomain

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultData {

    fun map(mapper: ResultDataToDomainMapper): ResultDomain

    fun isSuccess(): Boolean
}