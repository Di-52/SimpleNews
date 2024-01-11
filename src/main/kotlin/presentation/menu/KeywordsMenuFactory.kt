package presentation.menu

/**
 * @author Demitrist on 10.03.2023
 **/

interface KeywordsMenuFactory : MenuFactory {

    fun putKeywords(keys: List<String>)
}