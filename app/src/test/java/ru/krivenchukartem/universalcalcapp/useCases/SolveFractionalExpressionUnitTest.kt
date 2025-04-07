package ru.krivenchukartem.universalcalcapp.useCases

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.krivenchukartem.universalcalcapp.domain.parsers.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.useCases.SolveFractionalExpressionUseCase

class SolveFractionalExpressionUnitTest {
    @Test
    fun invoke_defaultTest(){
        val expression = "9/10 + 3/10"
        val useCase = SolveFractionalExpressionUseCase(FractionalParser())
        val result = useCase(expression)
        val expected = "6/5"

        assertEquals(expected, result)
    }
}