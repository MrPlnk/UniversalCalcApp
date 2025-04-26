package ru.krivenchukartem.universalcalcapp.domain.calculator.parser

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenParsingService
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.DefaultParsedTokenCompiler
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.TypeParserRegistry
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import kotlin.test.assertEquals

class ParsedTokenCompilerUnitTest {
    @Test
    fun compile_defaultTest(){
        val tokens = listOf(
            Token("4/7", Token.Type.FRACTIONAL_LITERAL, Token.OperatorAssociativity.NONE),
            Token("+", Token.Type.OPERATOR, Token.OperatorAssociativity.LEFT),
            Token("square", Token.Type.FUNCTION, Token.OperatorAssociativity.NONE),
            Token("(", Token.Type.L_PARENTHESIS, Token.OperatorAssociativity.NONE),
            Token("4/13", Token.Type.FRACTIONAL_LITERAL, Token.OperatorAssociativity.NONE),
            Token("+", Token.Type.OPERATOR, Token.OperatorAssociativity.LEFT),
            Token("3/4", Token.Type.FRACTIONAL_LITERAL, Token.OperatorAssociativity.NONE),
            Token(")", Token.Type.R_PARENTHESIS, Token.OperatorAssociativity.NONE)
        )
        val tokenCompiler = DefaultParsedTokenCompiler(
            parsingService = TokenParsingService(TypeParserRegistry(listOf(FractionalParser))),
            rpn = RPN(),
        )
        val result = tokenCompiler.compile(tokens)
        val expected = listOf(
            ParsedToken("4/7", ParsedToken.Type.LITERAL, NumberFractional(4, 7)),
            ParsedToken("4/13", ParsedToken.Type.LITERAL, NumberFractional(4, 13)),
            ParsedToken("3/4", ParsedToken.Type.LITERAL, NumberFractional(3, 4)),
            ParsedToken("+", ParsedToken.Type.OPERATOR),
            ParsedToken("square", ParsedToken.Type.FUNCTION),
            ParsedToken("+", ParsedToken.Type.OPERATOR),
        )
        assertEquals(expected, result)
    }
}