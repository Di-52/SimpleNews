package data

import data.models.ResultData

/**
 * @author Demitrist on 11.02.2023
 **/

interface CloudService {

    suspend fun fetch(): ResultData
}