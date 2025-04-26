package ru.krivenchukartem.universalcalcapp.domain.calculator.common.service

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.isLiteral
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Tokenizer
import javax.inject.Inject

interface TokenTypeExtractor  {
    fun extractLiteralTypes(tokens: List<Token>): List<Token.Type>
    fun extractLiteralTypes(literal: String): List<Token.Type>

    fun isOnlyOneLiteralType(tokens: List<Token>): Boolean
    fun isOnlyOneLiteralType(literal: String): Boolean

    fun extractTypes(tokens: List<Token>): List<Token.Type>
    fun extractTypes(literal: String): List<Token.Type>
}

class DefaultTokenTypeExtractor @Inject constructor(
    private val tokenizer: Tokenizer
) : TokenTypeExtractor {
    override fun extractLiteralTypes(tokens: List<Token>): List<Token.Type> {
        return tokens.filter { it.isLiteral() }.map { it.type }.toList()
    }

    override fun extractLiteralTypes(expression: String): List<Token.Type> {
        val tokens = tokenizer.tokenize(expression)
        return extractLiteralTypes(tokens)
    }

    override fun isOnlyOneLiteralType(tokens: List<Token>): Boolean {
        val types = extractLiteralTypes(tokens)
        return types.isEmpty() || types.all { it == types.first() }
    }

    override fun isOnlyOneLiteralType(expression: String): Boolean {
        val tokens = tokenizer.tokenize(expression)
        return isOnlyOneLiteralType(tokens)
    }

    override fun extractTypes(tokens: List<Token>): List<Token.Type> {
        return tokens.map { it.type }
    }

    override fun extractTypes(expression: String): List<Token.Type> {
        val tokens = tokenizer.tokenize(expression)
        return extractTypes(tokens)
    }
}