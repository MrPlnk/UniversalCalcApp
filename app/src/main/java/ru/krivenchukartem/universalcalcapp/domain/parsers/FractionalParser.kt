package ru.krivenchukartem.universalcalcapp.domain.parsers

import ru.krivenchukartem.universalcalcapp.domain.entity.Expression
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional

interface BaseFractionalParser{
    fun parse(expression: String): Expression<NumberFractional>
}

class FractionalParser: BaseFractionalParser {
    override fun parse(expression: String): Expression<NumberFractional> {
        val list = mutableListOf<NumberFractional>()
        var action = ""
        val objs = expression.split(" ")
        for (elem in objs){
            if (elem.contains(NumberFractional.delimiter)){
                val fractional = elem.split(NumberFractional.delimiter)
                list.add(NumberFractional(fractional[0].toInt(), fractional[1].toInt()))
            }
            else{
                action = elem
            }
        }
        if (list.size == 1){
            list.add(list[0])
        }
        return Expression<NumberFractional>(list.toList(), action)
    }
}