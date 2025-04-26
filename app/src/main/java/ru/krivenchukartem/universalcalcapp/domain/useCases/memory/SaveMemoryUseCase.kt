package ru.krivenchukartem.universalcalcapp.domain.useCases.memory

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.OperationMemory
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.isLiteral
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenTypeExtractor
import ru.krivenchukartem.universalcalcapp.domain.calculator.parser.RPN
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Tokenizer
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import javax.inject.Inject


class SaveMemoryUseCase @Inject constructor(
    private val memoryRepository: OperationMemoryRepository,
    private val tokenizer: Tokenizer,
    private val tokenTypeExtractor: TokenTypeExtractor,
    private val rpn: RPN
) {
    operator fun invoke(list: List<Token>): Result<Unit> = runCatching {
        val postfix = rpn.toRPN(list)
        val literals = postfix.filter { it.isLiteral() }
        if (literals.isEmpty()) return@runCatching
        val opCount = postfix.count { it.type == Token.Type.OPERATOR }

        val operand = if (opCount <= 1) literals.last() else literals.first()

        memoryRepository.saveMemory(
            operand.type,
            OperationMemory(
                operand   = operand
            )
        )
    }

    operator fun invoke(expression: String): Result<Unit> = runCatching{
        val tokens = tokenizer.tokenize(expression)
        invoke(tokens)
    }
}