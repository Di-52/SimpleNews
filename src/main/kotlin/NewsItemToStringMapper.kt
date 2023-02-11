import java.time.LocalDateTime

/**
 * @author Demitrist on 11.02.2023
 **/

interface NewsItemToStringMapper : Abstract.Mapper {

    fun map(id:Int,title: String, description: String, date: LocalDateTime, keywords: List<String>): String

    class Base : NewsItemToStringMapper {
        override fun map(id:Int,title: String, description: String, date: LocalDateTime, keywords: List<String>): String {
            var dateString = "" + date.dayOfMonth + " " + date.month.toString()
                .lowercase() + " " + date.year + " at " + date.hour + ":" + if(date.minute< 10) "00" else ""+date.minute
            var result = "$id - \n"
            result += "\t$title ($dateString)\n"
            result += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
            result += description + "\n"
            result += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
            if (keywords.size > 0) {
                result += "Keys: "
                keywords.forEach { result += "[$it] " }
                result += "\n"
            }
            result += "\n\n"
            return result
        }
    }
}