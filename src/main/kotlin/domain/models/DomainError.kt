package domain.models

/**
 * @author Demitrist on 12.02.2023
 **/

interface DomainError {

    class ConnectionError(val message: String) : DomainError
    class NoResult(val location: String) : DomainError
    object GenericError : DomainError
}