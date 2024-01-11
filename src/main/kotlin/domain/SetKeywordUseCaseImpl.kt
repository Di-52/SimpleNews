package domain

/**
 * @author Demitrist on 10.03.2023
 **/

class SetKeywordUseCaseImpl(
    private val repository: SpecialNewsRepository,
    private val callback: (String) -> Unit
) : SetKeywordUseCase {

    override fun keyword(key: String) {
        val isSet = repository.keyword(key)
        callback("Key [$key] is ${if (isSet) " added." else " removed."})")
    }
}