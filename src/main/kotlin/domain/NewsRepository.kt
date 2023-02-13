package domain

import data.models.ResultData
import domain.models.ResultDomain

/**
 * @author Demitrist on 11.02.2023
 **/

interface NewsRepository {

    suspend fun fetchNews(): ResultDomain
}