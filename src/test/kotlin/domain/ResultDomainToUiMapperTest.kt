package domain

/**
 * @author Demitrist on 21.02.2023
 */

class ResultDomainToUiMapperTest {
//
//    private val domainNews = NewsListDomain(
//        name = "name",
//        location = "location",
//        news = emptyList(),
//        keywords = listOf("key1")
//    )
//
//    @Test
//    fun test_with_data() {
//        val listMapper = FakeListMapper()
//        val listMapperResult = "uiResult"
//        listMapper.result = listMapperResult
//        val formatter = FakeFormatter()
//        val mapper = ResultDomainMapper.Base(listMapper = listMapper, formatter = formatter)
//
//        val result = mapper.map(domainNews)
//
//        assertEquals(expected = ResultUi(listMapperResult), actual = result)
//        assertEquals(expected = 1, actual = listMapper.mapperCalledCount)
//        assertEquals(expected = 1, actual = formatter.formatterCalledCount)
//    }
//
//    @Test
//    fun test_with_error() {
//        val errors = listOf(
//            DomainError.GenericError(message = ""),
//            DomainError.ConnectionError(message = "error reason"),
//            DomainError.NoResult(location = "location")
//        )
//        val expected = listOf(
//            "Something went wrong.",
//            "Can't load news, cause: error reason. Check you internet connection.",
//            "Nothing new in location at this time."
//        )
//        val listMapper = FakeListMapper()
//        val formatter = FakeFormatter()
//        val mapper = ResultDomainMapper.Base(listMapper = listMapper, formatter = formatter)
//        var result: ResultUi
//
//        errors.forEachIndexed { index, domainError ->
//
//            result = mapper.map(domainError)
//
//            assertEquals(expected = ResultUi(expected[index]), actual = result)
//            assertEquals(expected = index + 1, actual = formatter.formatterCalledCount)
//        }
//    }
//
//    private class FakeListMapper : NewsListDomain.Mapper<String> {
//        var mapperCalledCount = 0
//        var result = ""
//
//        override fun map(name: String, location: String, news: List<NewsItemDomain>, keywords: List<String>): String {
//            mapperCalledCount++
//            return result
//        }
//    }
//
//    private class FakeFormatter : Formatter<String, String> {
//        var formatterCalledCount = 0
//
//        override fun format(input: String): String {
//            formatterCalledCount++
//            return input
//        }
//    }
}