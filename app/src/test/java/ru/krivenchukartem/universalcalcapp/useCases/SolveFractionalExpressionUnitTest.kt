package ru.krivenchukartem.universalcalcapp.useCases

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.useCases.SolveFractionalExpressionUseCase

class SolveFractionalExpressionUnitTest {
    @Test
    fun invoke_defaultTest(){
        val expression = "[3/10] + square([2/10] + [4/10])"
        val result = SolveFractionalExpressionUseCase().invoke(expression)
        val expected = NumberFractional(66, 100).toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_memoryUsage(){
        val expression = "[3/10] + "
        val result = SolveFractionalExpressionUseCase().invoke(expression)
        val expected = NumberFractional(3, 5).toString()

        assertEquals(expected, result)
    }
}