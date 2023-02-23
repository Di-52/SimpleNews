package presentation

/**
 * @author Demitrist on 11.02.2023
 **/
interface UserInput {

    fun wait(valFrom: Int, valTo: Int): Int

    class Base(private val parser: InputParser) : UserInput {

        override fun wait(valFrom: Int, valTo: Int): Int {
            var res = -1
            while (res !in valFrom..valTo) {
                print("Choose: ")
                val typed = readln()
                res = parser.parse(typed)
                if (res < 0)
                    println("...invalid input...")
            }
            return res
        }
    }
}