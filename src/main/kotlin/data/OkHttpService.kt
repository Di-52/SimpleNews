package data

import data.models.NewsListData
import data.models.ResultData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * @author Demitrist on 09.03.2023
 **/

class OkHttpService(private val responseUrl: String) : CloudService {

    override suspend fun fetch(): ResultData {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder().url(url = responseUrl).build()
            val response = client.newCall(request).execute()
            val gson = Gson()
            val result: NewsListData =
                gson.fromJson(response.body?.string(), NewsListData::class.java)
            SuccessResultData(result)
        } catch (e: Exception) {
            FailResultData(e)
        }
    }
}