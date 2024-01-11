package data

import data.models.ResultData

/**
 * @author Demitrist on 09.03.2023
 **/

interface MutableDataSource : DataSource {

    fun save(dataToSave: ResultData)

    fun haveData(): Boolean
}