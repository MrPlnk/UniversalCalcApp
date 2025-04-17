package ru.krivenchukartem.universalcalcapp.calculator.registry.parsers.cpecific

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import kotlin.test.assertEquals

class FractionalParserUnitTest {
    @Test
    fun parse_defaultTest(){
        val literal = "8/9"
        val result = FractionalParser.parse(literal)
        val expected = NumberFractional(8, 9)
        assertEquals(expected, result)
    }
}