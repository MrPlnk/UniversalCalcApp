package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.Parser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.ParserRegistryExceptions

object FractionalParser: Parser<NumberFractional> {
    override val name: String
        get() = "fractional"

    override val supportedTokenType: Token.Type
        get() = Token.Type.FRACTIONAL_LITERAL


    private val pattern = Regex("""^\s*([+-]?\d+)\s*/\s*([+-]?\d+)\s*$""")

    override fun parse(literal: String): NumberFractional {
        val match = pattern.matchEntire(literal)
            ?: throw ParserRegistryExceptions.CantConvertNumber(literal, name)

        val numerator = match.groupValues[1].toIntOrNull()
            ?: throw ParserRegistryExceptions.CantConvertNumber(literal, name)
        val denominator = match.groupValues[2].toIntOrNull()
            ?: throw ParserRegistryExceptions.CantConvertNumber(literal, name)

        if (denominator == 0) {
            throw ParserRegistryExceptions.CantConvertNumber(literal, name)
        }

        return NumberFractional(numerator, denominator)
    }
}
