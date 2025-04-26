package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import kotlin.test.assertEquals

class ComplexParserUnitTest {
    @Test
    fun parse_defaultTest(){
        val literal = "8+9i"
        val result = ComplexParser.parse(literal)
        val expected = NumberComplex(8.0, 9.0)
        assertEquals(expected, result)
    }

    @Test
    fun parse_extraSpaces(){
        val literal = " 8 + 9 i "
        val result = ComplexParser.parse(literal)
        val expected = NumberComplex(8.0, 9.0)
        assertEquals(expected, result)
    }

    @Test
    fun parse_explicitSings(){
        val literal = "-8 -9i "
        val result = ComplexParser.parse(literal)
        val expected = NumberComplex(-8.0, -9.0)
        assertEquals(expected, result)
    }

    @Test
    fun parse_onlyImPartIsZero(){
        val literal = "-8 + 0i"
        val result = ComplexParser.parse(literal)
        val expected = NumberComplex(-8.0, 0.0)
        assertEquals(expected, result)
    }

    @Test
    fun parse_onlyRePartIsZero(){
        val literal = "-0 + 4i"
        val result = ComplexParser.parse(literal)
        val expected = NumberComplex(-0.0, 4.0)
        assertEquals(expected, result)
    }

    @Test
    fun parse_fractionalParts(){
        val literal = "-8.1234 + 0.1234i"
        val result = ComplexParser.parse(literal)
        val expected = NumberComplex(-8.1234, 0.1234)
        assertEquals(expected, result)
    }
}