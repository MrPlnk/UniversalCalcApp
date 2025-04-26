package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.Parser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.ParserRegistryExceptions

object ComplexParser: Parser<NumberComplex> {
    override val name: String
        get() = "complex"

    override val supportedTokenType: Token.Type
        get() = Token.Type.COMPLEX_LITERAL

    private val pattern = Regex("""^([+-]?\d+(\.\d+)?)([+-]\d+(\.\d+)?)i$""")

    override fun parse(literal: String): NumberComplex {
        val sanitized = literal.replace("\\s+".toRegex(), "")
        val match = pattern.matchEntire(sanitized)
            ?: throw ParserRegistryExceptions.CantConvertNumber(literal, name)

        val realPart = match.groupValues[1].toDouble()
        val imagPart = match.groupValues[3].toDouble()

        return NumberComplex(realPart, imagPart)
    }
}
