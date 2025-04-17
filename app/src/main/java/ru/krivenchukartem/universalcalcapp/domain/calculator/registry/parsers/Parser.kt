package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

interface Parser<T: NumberBase<T>> {
    val name: String
    fun parse(literal: String): T
}