package presentation

import core.DispatchersList
import data.CloudService
import data.ResultDataToDomainMapper
import domain.NewsRepository
import kotlinx.coroutines.*

/**
 * @author Demitrist on 11.02.2023
 **/

private val url = "https://api2.kiparo.com/static/it_news.json"

val handler = CoroutineExceptionHandler { _, error ->
    println("From handler: $error")
}
/*
suspend fun test() {
    println("Start test")
    delay(1)
    var res = CloudService.OkHttpService(url).fetch()
//    repeat(10) {
//        println("From test ")
//        delay(500)
//    }
    println("Esd test")
}

fun main() {
    var job = CoroutineScope(Dispatchers.IO).launch {
        try {
            withTimeout(3000) {
                test()
            }
            println("From coroutine try")
        } catch (e: Exception) {
            println("From coroutine catch $e")
        } finally {
            println("From coroutine finaly")
        }
    }
//    job.start()
    readln()
    println("End")
}

*/

fun main(){

    readlnOrNull()
    val repo = NewsRepository.Base(
        CloudService.OkHttpService(url),
        DispatchersList.Base(),
        ResultDataToDomainMapper.Base())
    repo.fetchNews { showResult(it) }
    var r = readln()
}

fun showResult(str:String){
    println(str)
}
