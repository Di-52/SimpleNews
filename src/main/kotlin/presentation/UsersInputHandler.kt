package presentation

/**
 * @author Demitrist on 10.03.2023
 **/

class UsersInputHandler(private val parser: InputParser) : UserInput {

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