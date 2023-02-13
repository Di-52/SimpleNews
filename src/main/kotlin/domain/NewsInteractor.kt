package domain

import presentation.models.ResultUi

/**
 * @author Demitrist on 13.02.2023
 **/

interface NewsInteractor {
    fun news(callBack: (String) -> Unit)
    fun filter(key: String)
    fun sort(orderDesc: Boolean)
    fun allNews(callBack: (String) -> Unit)
}