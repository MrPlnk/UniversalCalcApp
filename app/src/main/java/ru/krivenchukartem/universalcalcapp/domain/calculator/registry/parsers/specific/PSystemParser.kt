package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.Parser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem
import ru.krivenchukartem.universalcalcapp.domain.errors.ParserRegistryExceptions

object PSystemParser: Parser<NumberPSystem>{
    override val name: String
        get() = "pSystem"

    override fun parse(literal: String): NumberPSystem {
        val parts = literal.split(NumberPSystem.delimiter)
        if (parts.size != 2){
            throw ParserRegistryExceptions.CantConvertNumber(literal, name)
        }
        return NumberPSystem(parts[0], parts[1].toInt())
    }
}