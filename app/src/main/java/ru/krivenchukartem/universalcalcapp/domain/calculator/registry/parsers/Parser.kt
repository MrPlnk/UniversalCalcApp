package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

interface Parser<T: NumberBase<T>> {
    val name: String
    val supportedTokenType: Token.Type
    fun parse(literal: String): T
}