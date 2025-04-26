package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.Parser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.ParserRegistryExceptions

object PSystemParser: Parser<NumberPSystem>{
    override val name: String
        get() = "pSystem"

    override val supportedTokenType: Token.Type
        get() = Token.Type.PSYSTEM_LITERAL

    private val pattern = Regex(
        """^\s*([0-9A-Za-z]+(?:\.[0-9A-Za-z]+)?)\s*,\s*([0-9]|[1-2][0-9]|3[0-6])\s*$"""
    )

    override fun parse(literal: String): NumberPSystem {
        val match = pattern.matchEntire(literal)
            ?: throw ParserRegistryExceptions.CantConvertNumber(literal, name)

        val digitString = match.groupValues[1]
        val base = match.groupValues[2].toInt()

        // Функция перевода символа в его числовое значение:
        fun charValue(c: Char): Int = when (c) {
            in '0'..'9' -> c - '0'
            in 'A'..'Z' -> c - 'A' + 10
            in 'a'..'z' -> c - 'a' + 10
            else -> -1
        }

        // Проверяем, что все символы (кроме точки) допустимы в данном base
        digitString.forEach { c ->
            if (c != '.') {
                val v = charValue(c)
                if (v < 0 || v >= base) {
                    throw ParserRegistryExceptions.CantConvertNumber(literal, name)
                }
            }
        }

        return NumberPSystem(digitString, base)
    }
}