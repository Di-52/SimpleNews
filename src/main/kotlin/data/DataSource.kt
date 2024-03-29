package data

import data.models.ResultData

/**
 * @author Demitrist on 14.02.2023
 **/

interface DataSource {

    suspend fun fetchData(): ResultData
}