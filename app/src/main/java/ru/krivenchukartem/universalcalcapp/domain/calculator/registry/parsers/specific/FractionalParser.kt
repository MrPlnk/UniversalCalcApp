package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.Parser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.errors.ParserRegistryExceptions

object FractionalParser: Parser<NumberFractional> {
    override val name: String
        get() = "fractional"

    override fun parse(literal: String): NumberFractional {
        val parts = literal.split(NumberFractional.delimiter)
        if (parts.size != 2){
            throw ParserRegistryExceptions.CantConvertNumber(literal, name)
        }
        return NumberFractional(parts[0].toInt(), parts[1].toInt())
    }

}