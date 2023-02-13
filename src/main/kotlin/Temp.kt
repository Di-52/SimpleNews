private val url = "https://api2.kiparo.com/static/it_news.json"
/*
fun main(){


/*
    var client = OkHttpClient()
    var req = Request.Builder().url(url = url).build()
    var resp = client.newCall(req).execute()
    var gson = Gson()
    var result : NewsListData = gson.fromJson(resp.body?.string(), NewsListData::class.java)

    val raw = result.map(ServerResponseToNewsListMapper.Base(ResponseNewsItemToNewsItemMapper.Base()))
*/
    /*
    val service = CloudService.OkHttpService(url)
    GlobalScope.launch {
        val raw: ServerResponse = service.fetch()
        var r = raw.map(ServerResponceToDomainMapper.Base())

        raw.sort(SortOrder.DATE_ASK)
        raw.byKeyword("apple")

        println(raw.map(NewsListToStringMapper.Base(NewsItemToStringMapper.Base())))
    }
*/*/