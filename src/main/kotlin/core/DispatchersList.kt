package core

import kotlinx.coroutines.CoroutineDispatcher


/**
 * @author Demitrist on 11.02.2023
 **/

interface DispatchersList {

    fun io(): CoroutineDispatcher

    fun ui(): CoroutineDispatcher
}