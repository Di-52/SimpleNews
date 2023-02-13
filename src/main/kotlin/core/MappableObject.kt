package core

/**
 * @author Demitrist on 12.02.2023
 **/

interface MappableObject<T, M : Mapper> {
    fun map(mapper: M): T
}