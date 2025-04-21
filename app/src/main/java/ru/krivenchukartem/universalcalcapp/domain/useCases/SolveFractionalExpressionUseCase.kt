package ru.krivenchukartem.universalcalcapp.domain.useCases

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.mappers.TokenParsingService
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.ParsedTokenCompiler
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.processor.RPNProcessor
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FunctionRegistryProvider
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.TypeParserRegistry
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.PSystemParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.UniversalTokenizer
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository

class SolveFractionalExpressionUseCase(
    private val memoryRepository: OperationMemoryRepository
) {
    operator fun invoke(expressionStr: String): String {
        val rpn = RPN()
        val typeParserRegistry = TypeParserRegistry(listOf(
            FractionalParser,
            ComplexParser,
            PSystemParser
            )
        )
        val parsingService = TokenParsingService(typeParserRegistry)
        val parsedTokenCompiler = ParsedTokenCompiler(parsingService, rpn)
        val processor = RPNProcessor(FunctionRegistryProvider.fractionalFunctionRegistry)
        val extendExpression = ExtendTokenExpressionUseCase(memoryRepository)

        val tokens = UniversalTokenizer().tokenize(expressionStr)
        val extendedTokens = extendExpression(tokens)
        val parsedTokens = parsedTokenCompiler.compile(extendedTokens)
        val resultToken = processor.evaluate(parsedTokens as List<ParsedToken<NumberFractional>>)
        return resultToken.value.toString()
    }

}