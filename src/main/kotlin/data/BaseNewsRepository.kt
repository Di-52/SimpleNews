package data

import core.DispatchersList
import data.models.ResultData
import domain.NewsRepository
import kotlinx.coroutines.*

/**
 * @author Demitrist on 13.02.2023
 **/

class BaseNewsRepository(
    private val cloudService: CloudService,
    private val dispatchers: DispatchersList,
    private val mapper: ResultDataToDomainMapper
) : NewsRepository {
    private lateinit var res: ResultData

    override fun fetchNews(callback: (String) -> Unit) {
        CoroutineScope(dispatchers.io()).launch {
            try {
                withTimeout(5000) {
                    delay(1)
                    res = cloudService.fetch()
                }
            } catch (e: Exception) {
                res = ResultData.Fail(e)
            } finally {
                withContext(dispatchers.ui()) {
                    callback.invoke(res.toString()) //todo fix returned value
                }
            }
            //todo: mapping and return to UI
        }
    }
}