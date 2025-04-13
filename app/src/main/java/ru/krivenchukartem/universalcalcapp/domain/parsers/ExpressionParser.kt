package ru.krivenchukartem.universalcalcapp.domain.parsers

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

interface ExpressionParser<T: NumberBase<T>> {
    fun parse(expression: String): T
}