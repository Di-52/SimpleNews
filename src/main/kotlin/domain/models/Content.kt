package domain.models

interface Content {

    fun haveNoNews(): Boolean
    fun location(): String
    fun keywords(): List<String>
}