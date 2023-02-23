package domain.models

/**
 * @author Demitrist on 12.02.2023
 **/

interface DomainError {

    data class ConnectionError(val message: String) : DomainError
    data class NoResult(val location: String) : DomainError
    data class GenericError(val message: String) : DomainError
}