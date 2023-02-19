package data

/**
 * @author Demitrist on 16.02.2023
 **/

data class NoDataException(private val errorMessage:String) : Exception(errorMessage)