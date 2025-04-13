package ru.krivenchukartem.universalcalcapp.calculator.tokenizer

import org.junit.Assert
import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.UniversalTokenizer

class TokenizerUnitTest {
    @Test
    fun tokenize_defaultTest(){
        val expression = "[4/7] + square([4/13] + [3/4])"
        val tokens = UniversalTokenizer().tokenize(expression)
        val result = mutableListOf<String>()
        tokens.forEach {
            result += it.token
        }
        val expected = listOf<String>(
            "4/7", "+", "square", "(", "4/13", "+", "3/4", ")"
        )

        Assert.assertEquals(expected, result)
    }
}