package ru.krivenchukartem.universalcalcapp.calculator.parser

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.UniversalTokenizer
import kotlin.test.assertEquals

class RPNUnitTest {
    @Test
    fun toRPN_defaultTest(){
        val expression = "[4/7] + square([4/13] + [3/4])"
        val tokens = UniversalTokenizer().tokenize(expression)
//        val tokens = listOf(
//            Token("4/7", Token.Type.FRACTIONAL_LITERAL, Token.OperatorAssociativity.NONE),
//            Token("4/13", Token.Type.FRACTIONAL_LITERAL, Token.OperatorAssociativity.NONE),
//            Token("3/4", Token.Type.FRACTIONAL_LITERAL, Token.OperatorAssociativity.NONE),
//            Token("+", Token.Type.OPERATOR, Token.OperatorAssociativity.LEFT),
//            Token("square", Token.Type.FUNCTION, Token.OperatorAssociativity.NONE),
//            Token("+", Token.Type.OPERATOR, Token.OperatorAssociativity.LEFT)
//        )
        val rpn = RPN().toPRN(tokens)
        val result = mutableListOf<String>()
        rpn.forEach {
            result.add(it.token)
        }
        val expected = listOf(
            "4/7", "4/13", "3/4", "+", "square", "+"
        )
        assertEquals(expected, result.toList())
    }
}