package data

import domain.NewsRepository
import domain.SpecialNewsRepository
import domain.mappers.NewsItemMatchAnyKeyword
import domain.mappers.NewsItemMatchVisibility
import domain.mappers.NewsSeparator
import domain.mappers.NewsSorter
import domain.models.ResultDomain

/**
 * @author Demitrist on 10.03.2023
 **/

class SpecialNewsRepositoryImpl(
    private val repository: NewsRepository,
) : SpecialNewsRepository {
    private val keys = mutableListOf<String>()
    private var sortOrderDesc = true

    override suspend fun fetchNews(): ResultDomain {
        var result = repository.fetchNews()
        if (keys.isNotEmpty()) {
            val filter = NewsSeparator(
                listOf(
                    NewsItemMatchVisibility(),
                    NewsItemMatchAnyKeyword(keys)
                )
            )
            result = result.filteredNews(mapper = filter)
        }
        val sorter = NewsSorter(sortOrderDesc = sortOrderDesc)
        result = result.sortedNews(mapper = sorter)
        return result
    }

    override fun keyword(key: String) =
        if (keys.contains(key)) {
            keys.remove(key)
            false
        } else {
            keys.add(key)
            true
        }

    override fun sortOrder(orderDesc: Boolean) {
        sortOrderDesc = orderDesc
    }
}