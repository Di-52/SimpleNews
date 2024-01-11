package domain

/**
 * @author Demitrist on 11.03.2023
 **/

class SetSortOrderUseCaseImpl(
    private val repository: SpecialNewsRepository,
    private val callback: (String) -> Unit
) : SetSortOrderUseCase {

    override fun setSortOrder(sortOrderDesc: Boolean) {
        repository.sortOrder(orderDesc = sortOrderDesc)
        callback.invoke("Sort order set to ${if (sortOrderDesc) " DESC" else " ASK"}")
    }
}