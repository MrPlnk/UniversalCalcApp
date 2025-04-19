package ru.krivenchukartem.universalcalcapp.calculator.processor

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.processor.RPNProcessor
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FunctionRegistryProvider
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import kotlin.test.assertEquals

class RPNProcessorUnitTest {
    @Test
    fun evaluate_defaultTest(){
        val tokens = listOf(
            ParsedToken("1/3", ParsedToken.Type.LITERAL, NumberFractional(1, 3)),
            ParsedToken("2/3", ParsedToken.Type.LITERAL, NumberFractional(2, 3)),
            ParsedToken("2/3", ParsedToken.Type.LITERAL, NumberFractional(2, 3)),
            ParsedToken("+", ParsedToken.Type.OPERATOR),
            ParsedToken("square", ParsedToken.Type.FUNCTION),
            ParsedToken("+", ParsedToken.Type.OPERATOR),
        )
        val expected = ParsedToken("19/9", ParsedToken.Type.LITERAL, NumberFractional(19, 9))
        val processor = RPNProcessor(functionRegistry = FunctionRegistryProvider.fractionalFunctionRegistry)
        val result = processor.evaluate(tokens)

        assertEquals(expected, result)
    }

}