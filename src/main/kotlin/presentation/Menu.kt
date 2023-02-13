package presentation

/**
 * @author Demitrist on 11.02.2023
 **/

class Menu() {

    fun showMenu(level:Int = 1){
        when(level){
            1->println("Type one digit")
            2->{
                println("1. Show all news")
                println("2. Show sorted news (Date asc)")
                println("3. Show sorted news (Date desc)")
                println("3. Filter by keyword")
            }
        }
    }
    fun showKeywords(keys:List<String>){
        println("Select word for filter: ")
        keys.forEachIndexed{index,key ->
            var i = index+1
            println("$i. $key")
        }
    }
}