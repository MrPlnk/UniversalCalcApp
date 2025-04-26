package ru.krivenchukartem.universalcalcapp.domain.useCases

import org.junit.Test
import org.junit.Assert.assertEquals
import ru.krivenchukartem.universalcalcapp.data.calculator.memory.InMemoryOperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.DefaultTokenTypeExtractor
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenParsingService
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.DefaultParsedTokenCompiler
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FunctionRegistryProvider
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.TypeParserRegistry
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.PSystemParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.UniversalTokenizer
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.useCases.ExtendTokenExpressionUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.SolveUniversalExpressionUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.SaveMemoryUseCase

class SolveFractionalExpressionUnitTest {

    private val memoryRepository = InMemoryOperationMemoryRepository()
    private val typeParserRegistry = TypeParserRegistry(
        listOf(FractionalParser, ComplexParser, PSystemParser)
    )

    private val rpn = RPN()
    private val parsingService = TokenParsingService(typeParserRegistry)
    private val tokenizer = UniversalTokenizer()
    private val tokenTypeExtractor = DefaultTokenTypeExtractor(tokenizer)
    private val saveMemoryUseCase = SaveMemoryUseCase(memoryRepository, tokenizer, tokenTypeExtractor, rpn)

    private val extendTokenExpressionUseCase = ExtendTokenExpressionUseCase(tokenTypeExtractor)
    private val parsedTokenCompiler = DefaultParsedTokenCompiler(parsingService, rpn)
    private val functionRegistryProvider = FunctionRegistryProvider

    // 2. Инстанцируем UseCase один раз для всех тестов
    private val solve = SolveUniversalExpressionUseCase(
        tokenizer,
        parsedTokenCompiler,
        functionRegistryProvider,
        extendTokenExpressionUseCase
    )
    @Test
    fun invoke_defaultTest(){
        val expression = "[3/10] + square([2/10] + [4/10])"
        val result = solve(expression).getOrThrow()
        val expected = NumberFractional(66, 100).toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_extendUsage(){
        val expression = "[3/10] + "
        val result = solve(expression).getOrThrow()
        val expected = NumberFractional(3, 5).toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_memoryUsage(){
        val expression = "[3/10] + "
        var result = solve(expression).getOrThrow()
        result = solve(result).getOrThrow()
        val expected = NumberFractional(9, 10).toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_functionInMemory(){
        val expression = "[3/10] + square([3/10])"
        var result = solve(expression).getOrThrow()
        result = solve(result).getOrThrow()
        val expected = NumberFractional(48, 100).toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_resultIsZeroFractionalNumber(){
        val expression = "[-3/10] + [3/10]"
        var result = solve(expression).getOrThrow()
        val expected = NumberFractional(0, 1).toString()

        assertEquals(expected, result)
    }

    @Test
    fun invoke_resultIsZeroComplexNumber(){
        val expression = "[1+3i] + [1-3i]"
        var result = solve(expression).getOrThrow()
        val expected = NumberFractional(0, 0).toString()

        assertEquals(expected, result)
    }
}

