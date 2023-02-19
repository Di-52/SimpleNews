package presentation

/**
 * @author Demitrist on 11.02.2023
 **/

class UserInput(private val parser: InputParser) {

    fun wait(valFrom: Int, valTo: Int): Int {
        var res = -1
        while (res !in valFrom..valTo) {
            print("Choose: ")
            var typed = readln()
            res = parser.parse(typed)
            if (res < 0)
                println("...invalid input...")
        }
        return res
    }
}

class InputParser() {
    fun parse(input: String): Int =
        if (input.matches("[0-9]*".toRegex())) input.toInt() else -1
}
