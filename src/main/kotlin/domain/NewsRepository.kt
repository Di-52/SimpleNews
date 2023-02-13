package domain

import core.DispatchersList
import data.CloudService
import data.models.ResultData
import data.ResultDataToDomainMapper
import kotlinx.coroutines.*

/**
 * @author Demitrist on 11.02.2023
 **/

interface NewsRepository {

    fun fetchNews(callback: (String) -> Unit)
}