package ru.krivenchukartem.universalcalcapp.domain.calculator.parser

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenParsingService
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import javax.inject.Inject

interface ParsedTokenCompiler{
    fun compile(tokens: List<Token>): List<ParsedToken<*>>
}

class DefaultParsedTokenCompiler @Inject constructor(
    private val parsingService: TokenParsingService,
    private val rpn: BaseRPN
): ParsedTokenCompiler {
    override fun compile(tokens: List<Token>): List<ParsedToken<*>>{
        val order = rpn.toRPN(tokens)
        return order.map { parsingService.parseToken(it) }
    }
}