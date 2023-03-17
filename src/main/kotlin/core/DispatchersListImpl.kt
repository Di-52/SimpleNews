package core

/**
 * @author Demitrist on 10.03.2023
 **/

class DispatchersListImpl : DispatchersList {

    override fun io() = kotlinx.coroutines.Dispatchers.IO

    override fun ui() = kotlinx.coroutines.Dispatchers.Default
}