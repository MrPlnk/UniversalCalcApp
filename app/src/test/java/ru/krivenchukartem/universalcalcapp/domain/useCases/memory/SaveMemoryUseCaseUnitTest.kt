package ru.krivenchukartem.universalcalcapp.domain.useCases.memory

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.data.calculator.memory.InMemoryOperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.DefaultTokenTypeExtractor
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenParsingService
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.TypeParserRegistry
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.PSystemParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.UniversalTokenizer
import kotlin.test.assertEquals

class SaveMemoryUseCaseUnitTest {
    private val memoryRepository = InMemoryOperationMemoryRepository()
    private val rpn = RPN()
    private val tokenizer = UniversalTokenizer()
    private val tokenTypeExtractor = DefaultTokenTypeExtractor(tokenizer)
    private val saveMemoryUseCase = SaveMemoryUseCase(memoryRepository, tokenizer, tokenTypeExtractor, rpn)

    @Test
    fun invoke_defaultTest(){
        val expression = "[3/10] + [2/3]"
        val expected = "2/3"
        saveMemoryUseCase(expression)
        val memorySample = memoryRepository.getMemoryByType(Token.Type.FRACTIONAL_LITERAL) ?: throw Exception()
        val result = memorySample.operand.token.toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_complexExpression(){
        val expression = "[1/10] + [2/10]*[3/10]"
        val expected = "1/10"
        saveMemoryUseCase(expression)
        val memorySample = memoryRepository.getMemoryByType(Token.Type.FRACTIONAL_LITERAL) ?: throw Exception()
        val result = memorySample.operand.token.toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_oneArityFunctions(){
        val expression = "[1/10] + square([2/10])"
        val expected = "2/10"
        saveMemoryUseCase(expression)
        val memorySample = memoryRepository.getMemoryByType(Token.Type.FRACTIONAL_LITERAL) ?: throw Exception()
        val result = memorySample.operand.token.toString()

        assertEquals(expected, result)
    }
}