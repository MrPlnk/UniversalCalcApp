package ru.krivenchukartem.universalcalcapp.domain.calculator.common.service

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.isLiteral
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Tokenizer
import javax.inject.Inject

interface TokenTypeExtractor  {
    fun extract(tokens: List<Token>): List<Token.Type>
    fun extract(literal: String): List<Token.Type>
}

class DefaultTokenTypeExtractor @Inject constructor(
    private val tokenizer: Tokenizer
) : TokenTypeExtractor {
    override fun extract(tokens: List<Token>): List<Token.Type> {
        return tokens.filter { it.isLiteral() }.map { it.type }.toList()
    }

    override fun extract(expression: String): List<Token.Type> {
        val tokens = tokenizer.tokenize(expression)
        return extract(tokens)
    }
}