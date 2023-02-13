package data

import com.google.gson.Gson
import data.models.NewsListData
import data.models.ResultData
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * @author Demitrist on 11.02.2023
 **/

interface CloudService {

    suspend fun fetch(): ResultData

    class OkHttpService(private val responseUrl: String) : CloudService {
        override suspend fun fetch(): ResultData {
            return try {
                val client = OkHttpClient()
                val request = Request.Builder().url(url = responseUrl).build()
                val response = client.newCall(request).execute()
                val gson = Gson()
                val result: NewsListData = gson.fromJson(response.body?.string(), NewsListData::class.java)
                ResultData.Success(result)
            } catch (e: Exception) {
                ResultData.Fail(e)
            }
        }
    }
}