package data.models

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultData {

    data class Success(private val data: NewsListData) : ResultData
    data class Fail(private val e: Exception) : ResultData
}