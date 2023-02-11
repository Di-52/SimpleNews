/**
 * @author Demitrist on 10.02.2023
 **/

interface Abstract {

    interface MappableObject<T, M : Mapper> {

        fun map(mapper: M): T
    }

    interface FinalObject : MappableObject<Unit, Mapper.Empty>

    interface Mapper {

        class Empty : Mapper
    }
}