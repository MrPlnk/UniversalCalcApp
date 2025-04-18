package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.Parser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.errors.ParserRegistryExceptions

object ComplexParser: Parser<NumberComplex> {
    override val name: String
        get() = "complex"

    override val supportedTokenType: Token.Type
        get() = Token.Type.COMPLEX_LITERAL

    override fun parse(literal: String): NumberComplex {
        val parts: List<String>
        if (literal.contains(NumberComplex.delimiterPlus)){
            parts = literal.split(NumberComplex.delimiterPlus)
        }
        else if (literal.contains(NumberComplex.delimiterMinus)){
            parts = literal.split(NumberComplex.delimiterMinus)
        }
        else{
            throw ParserRegistryExceptions.CantConvertNumber(literal, name)
        }
        return NumberComplex(parts[0].toDouble(), parts[1].toDouble())
    }
}