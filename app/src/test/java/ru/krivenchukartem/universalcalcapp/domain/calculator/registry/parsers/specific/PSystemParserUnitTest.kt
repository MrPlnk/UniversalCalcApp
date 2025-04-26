package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.PSystemParser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem
import kotlin.test.assertEquals

class PSystemParserUnitTest {
    @Test
    fun parse_defaultTest(){
        val literal = "10, 10"
        val result = PSystemParser.parse(literal)
        val expected = NumberPSystem("10", 10)
        assertEquals(expected, result)
    }

    @Test
    fun parse_extraSpaces(){
        val literal = " A , 11 "
        val result = PSystemParser.parse(literal)
        val expected = NumberPSystem("A", 11)
        assertEquals(expected, result)
    }

    @Test
    fun parse_numberBaseHigherThenPassed(){
        val literal = "A, 11"
        val result = PSystemParser.parse(literal)
        val expected = NumberPSystem("A", 11)
        assertEquals(expected, result)
    }

    @Test
    fun parse_fractionalNumber(){
        val literal = "A.234, 11"
        val result = PSystemParser.parse(literal)
        val expected = NumberPSystem("A.234", 11)
        assertEquals(expected, result)
    }
}