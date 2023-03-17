package data

import data.models.ResultData
import kotlinx.coroutines.delay
import kotlinx.coroutines.withTimeout

/**
 * @author Demitrist on 13.02.2023
 **/

class CloudDataSource(private val service: CloudService) : DataSource {

    override suspend fun fetchData(): ResultData {
        val result: ResultData = try {
            withTimeout(5000) {
                delay(1)
                service.fetch()
            }
        } catch (e: Exception) {
            FailResultData(e)
        }
        return result
    }
}
