package ru.krivenchukartem.universalcalcapp.domain.useCases

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.ParsedTokenCompiler
import ru.krivenchukartem.universalcalcapp.domain.calculator.processor.RPNProcessor
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FunctionRegistryProvider
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Tokenizer
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException
import javax.inject.Inject

class SolveUniversalExpressionUseCase @Inject constructor(
    private val tokenizer: Tokenizer,
    private val parsedTokenCompiler: ParsedTokenCompiler,
    private val functionRegistryProvider: FunctionRegistryProvider,
    private val extendTokenExpressionUseCase: ExtendTokenExpressionUseCase
) {
    operator fun invoke(expressionStr: String): Result<String> = runCatching {
        val tokens = tokenizer.tokenize(expressionStr)
        val extended = extendTokenExpressionUseCase(tokens).getOrThrow()
        val parsedTokens = parsedTokenCompiler.compile(extended)

        val firstLiteral = parsedTokens.firstOrNull { it.type == ParsedToken.Type.LITERAL }
            ?: throw AppException.UseCasesException.ExpressionNotContainNumbers(expressionStr)

        val resultToken = when (firstLiteral.value) {
            is NumberFractional -> {
                @Suppress("UNCHECKED_CAST")
                val fracTokens = parsedTokens as List<ParsedToken<NumberFractional>>
                RPNProcessor(functionRegistryProvider.fractionalFunctionRegistry)
                    .evaluate(fracTokens)
            }
            is NumberComplex -> {
                @Suppress("UNCHECKED_CAST")
                val complexTokens = parsedTokens as List<ParsedToken<NumberComplex>>
                RPNProcessor(functionRegistryProvider.complexFunctionRegistry)
                    .evaluate(complexTokens)
            }
            is NumberPSystem -> {
                @Suppress("UNCHECKED_CAST")
                val pSysTokens = parsedTokens as List<ParsedToken<NumberPSystem>>
                RPNProcessor(functionRegistryProvider.pSystemFunctionRegistry)
                    .evaluate(pSysTokens)
            }
            else -> throw IllegalStateException("Unsupported number type")
        }

        resultToken.value.toString()
    }
}