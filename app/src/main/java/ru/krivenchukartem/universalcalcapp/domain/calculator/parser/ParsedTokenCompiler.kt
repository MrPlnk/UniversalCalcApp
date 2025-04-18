package ru.krivenchukartem.universalcalcapp.domain.calculator.parser

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.mappers.TokenParsingService
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token

class ParsedTokenCompiler(
    private val parsingService: TokenParsingService,
    private val rpn: BaseRPN
) {
    fun compile(tokens: List<Token>): List<ParsedToken<*>>{
        val order = rpn.toRPN(tokens)
        return order.map { parsingService.parseToken(it) }
    }
}