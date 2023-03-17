package presentation

class InputParser {

    fun parse(input: String): Int =
        if (input.matches("[0-9]*".toRegex())) input.toInt() else -1
}