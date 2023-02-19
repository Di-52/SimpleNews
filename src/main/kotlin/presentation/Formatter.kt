package presentation

/**
 * @author Demitrist on 16.02.2023
 **/

interface Formatter<S, R> {

    fun format(input: S): R

    class StringFormatter(private val maxLength: Int = 40) : Formatter<String, String> {
        override fun format(input: String): String {
            val parts = input.split("\n").toMutableList()
            val resultParts = mutableListOf<String>()
            parts.forEachIndexed { index, part ->
                var temp = part
                val res = mutableListOf<String>()
                while (temp.length > maxLength && temp.contains(" ")) {
                    for (i in maxLength downTo 0) {
                        if (temp[i].equals(' ')) {
                            res.add(temp.substring(0, i))
                            temp = temp.substring(i + 1)
                            break
                        }
                    }
                }
                res.add(temp)
                resultParts.add(res.joinToString("\n"))
            }
            return resultParts.joinToString("\n")
        }
    }
}