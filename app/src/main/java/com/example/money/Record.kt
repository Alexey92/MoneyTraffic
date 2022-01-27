package com.example.money

class Record {
    var sum: Int? = null
    var description: String? = null
    var category: Int? = null

    fun createLog(): String{
        var linkStr: String = ": "
        if (sum!! > 0)   linkStr += "+" // Если доход, то необходимо добавить знак +

        return description + linkStr + sum.toString()
    }
}