package ru.krivenchukartem.universalcalcapp

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.entity.Expression
import ru.krivenchukartem.universalcalcapp.domain.entity.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.parsers.FractionalParser
import org.junit.Assert.assertEquals
import kotlin.math.exp

class FractionalParserUnitTest {
    @Test
    fun parse_defaultExpression(){
        var expression = "9/10 + 3/10"
        var result = FractionalParser().parse(expression)
        var expected = Expression<NumberFractional>(
            listOf(
                NumberFractional(9, 10),
                NumberFractional(3, 10)
            ), "+")

        assertEquals(expected, result)
    }
}