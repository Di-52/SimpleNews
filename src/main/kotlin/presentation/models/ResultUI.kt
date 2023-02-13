package presentation.models

/**
 * @author Demitrist on 11.02.2023
 **/

interface ResultUI {

    fun map(): String

    class Success(private val result: String) : ResultUI {
        override fun map(): String = result
    }

    class Fail(private val message: String) : ResultUI {
        override fun map(): String = message
    }
}