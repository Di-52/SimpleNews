package data

import data.models.ResultData
import kotlinx.coroutines.*

/**
 * @author Demitrist on 13.02.2023
 **/

interface CloudDataSource {

    suspend fun fetchData(): ResultData

    class Base(private val service: CloudService) : CloudDataSource {

        override suspend fun fetchData(): ResultData {
            val result: ResultData = try {
                withTimeout(5000) {
                    delay(1)
                    service.fetch()
                }
            } catch (e: Exception) {
                ResultData.Fail(e)
            }
            return result
        }
    }
}