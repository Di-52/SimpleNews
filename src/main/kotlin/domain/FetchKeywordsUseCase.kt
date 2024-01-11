package domain

/**
 * @author Demitrist on 14.02.2023
 **/

interface FetchKeywordsUseCase {

    fun keywords(block: (List<String>) -> Unit)
}