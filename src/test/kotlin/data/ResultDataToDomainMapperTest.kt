package data

/**
 * @author Demitrist on 20.02.2023
 */

class ResultDataToDomainMapperTest {
//
//    @Test
//    fun test_map_filled_news_list() {
//        val listMapper = FakeNewsListDataToDomainMapper()
//        val mapper = ResultDataToDomainMapper(
//            newsMapper = NewsListDataToDomainMapper(
//                itemMapper = NewsItemDataToDomainMapper()
//            )
//        )
//        val newsData = NewsListData("name", "location", listOf(NewsItemData()))
//        val mappingResult = NewsListDomain("name", "location", listOf(
//            NewsItemDomain(1,"title", "description",
//            LocalDateTime.now(), emptyList(),true)), emptyList())
//        listMapper.result = mappingResult
//
//        val expected = SuccessResultDomain(mappingResult)
//        val actual = mapper.map(newsData)
//
//        assertEquals(expected = expected, actual = actual)
//        assertEquals(expected = 1, actual = listMapper.mapperCalledCount)
//    }
//
//    @Test
//    fun test_map_empty_news_list() {
//        val listMapper = FakeNewsListDataToDomainMapper()
//        val mapper = ResultDataToDomainMapper(
//            newsMapper = NewsListDataToDomainMapper(
//                itemMapper = NewsItemDataToDomainMapper()
//            )
//        )
//        val newsData = NewsListData("name", "location", listOf(NewsItemData()))
//        val mappingResult = NewsListDomain("name", "location", emptyList(), emptyList())
//        listMapper.result = mappingResult
//
//        val expected = SuccessResultDomain(news = mappingResult)
//        val actual = mapper.map(newsData)
//
//        assertEquals(expected = expected, actual = actual)
//        assertEquals(expected = 1, actual = listMapper.mapperCalledCount)
//    }
//
//    @Test
//    fun test_map_exception() {
//        val listMapper = FakeNewsListDataToDomainMapper()
//        val mapper = ResultDataToDomainMapper.Base(newsMapper = listMapper)
//        val data = Exception()
//
//        val actual = mapper.map(data)
//        val expected = ResultDomain.Fail(DomainError.ConnectionError(message = "Server is unreachable"))
//
//        assertEquals(expected = expected, actual = actual)
//        assertEquals(expected = 0, actual = listMapper.mapperCalledCount)
//    }
}
//
//private class FakeNewsListDataToDomainMapper : NewsListData.Mapper<NewsListDomain> {
//    var result: NewsListDomain? = null
//    var mapperCalledCount = 0
//
//    override fun map(name: String, location: String, news: List<NewsItemData>): NewsListDomain {
//        mapperCalledCount++
//        return result!!
//    }
//}