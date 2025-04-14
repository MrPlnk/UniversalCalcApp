package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

interface Parser {
    val name: String
    fun parse(literal: String): NumberBase<*>
}