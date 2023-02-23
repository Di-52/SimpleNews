package presentation

interface InputParser {

    fun parse(input: String): Int

    class Base : InputParser {
        override fun parse(input: String): Int =
            if (input.matches("[0-9]*".toRegex())) input.toInt() else -1
    }
}