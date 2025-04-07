package ru.krivenchukartem.universalcalcapp.domain.useCases

import ru.krivenchukartem.universalcalcapp.domain.entity.Expression
import ru.krivenchukartem.universalcalcapp.domain.entity.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.parsers.BaseFractionalParser

class SolveFractionalExpressionUseCase(private val fractionalParser: BaseFractionalParser) {
    operator fun invoke(expressionStr: String): String {
        return execute(fractionalParser.parse(expressionStr)).toString()
    }

    private fun execute(expression: Expression<NumberFractional>): NumberFractional{
        val (a, b) = expression.numbers
        return when (expression.action) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            else -> throw IllegalArgumentException("Неизвестная операция: ${expression.action}")
        }
    }
}