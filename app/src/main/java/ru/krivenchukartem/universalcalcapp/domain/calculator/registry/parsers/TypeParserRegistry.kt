package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token

class TypeParserRegistry(private val parsers: List<Parser<*>>) {
    private val map: Map<Token.Type, Parser<*>> = parsers
        .associateBy { it.supportedTokenType }

    fun getByType(type: Token.Type): Parser<*>? = map[type]
    fun getAll(): List<Parser<*>> = parsers
}