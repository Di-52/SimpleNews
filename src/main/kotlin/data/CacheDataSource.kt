package data

import data.models.ResultData

/**
 * @author Demitrist on 14.02.2023
 **/

class CacheDataSource() : DataSource.Mutable {
    private var data = mutableListOf<ResultData>()

    override fun haveData():Boolean = data.size > 0

    override suspend fun fetchData():ResultData{
        val result = if (data.isEmpty())  ResultData.Fail(e = NoDataException("Try to read empty cache"))
        else data.last()
        return result
    }

    override fun save(dataToSave: ResultData) {
        data.add(dataToSave)
    }
}