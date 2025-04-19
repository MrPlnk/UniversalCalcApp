package ru.krivenchukartem.universalcalcapp.domain.calculator.common.mappers

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.Parser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.TypeParserRegistry
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

class TokenParsingService(
    private val registry: TypeParserRegistry
) {
    @Suppress("UNCHECKED_CAST")
    fun <T: NumberBase<T>> parseToken(token: Token): ParsedToken<T>{
        val parser = registry.getByType(token.type) as Parser<T>?
        val value = parser?.parse(token.token)

        return ParsedToken(
            token = token.token,
            type = if (value != null){
                ParsedToken.Type.LITERAL
            } else {
                ParsedToken.Type.valueOf(token.type.name)
            },
            value = value
        )
    }
}