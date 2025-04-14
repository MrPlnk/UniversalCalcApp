package ru.krivenchukartem.universalcalcapp.domain.calculator.parser

import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Token
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase


fun <T : NumberBase<T>> Token.toParsedToken(parser: (String) -> T?): ParsedToken<T> {
    val value = when (this.type) {
        Token.Type.FRACTIONAL_LITERAL,
        Token.Type.COMPLEX_LITERAL,
        Token.Type.PSYSTEM_LITERAL -> parser(this.token)
        else -> null
    }

    return ParsedToken(
        token = this.token,
        type = ParsedToken.Type.valueOf(this.type.name),
        asc = ParsedToken.OperatorAssociativity.valueOf(this.asc.name),
        value = value
    )
}
