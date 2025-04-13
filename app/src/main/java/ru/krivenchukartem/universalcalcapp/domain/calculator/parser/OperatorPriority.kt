package ru.krivenchukartem.universalcalcapp.domain.calculator.parser

import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Token
import ru.krivenchukartem.universalcalcapp.domain.errors.TokenizerExceptions

object OperatorPrecedence {
    private val precedence = mapOf(
        "+" to 1,
        "-" to 1,
        "*" to 2,
        "/" to 2
    )

    fun getPrecedence(token: Token): Int =
        precedence[token.token] ?: throw TokenizerExceptions.InvalidOperator(token.token)

    fun isLeftAssociative(token: Token): Boolean =
        token.asc == Token.OperatorAssociativity.LEFT
}