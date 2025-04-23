package ru.krivenchukartem.universalcalcapp.domain.useCases.memory

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.OperationMemory
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.isLiteral
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Tokenizer
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import javax.inject.Inject


class SaveMemoryUseCase @Inject constructor(
    private val memoryRepository: OperationMemoryRepository,
    private val tokenizer: Tokenizer
) {
    operator fun invoke(list: List<Token>){
        if (list.size >= 3){
            val lastOpIndex = list.indexOfLast { it.type == Token.Type.OPERATOR }
            if (lastOpIndex != -1 && lastOpIndex < list.size - 1) {
                val lastOperation = list[lastOpIndex]
                val lastOperandTokens = list.subList(lastOpIndex + 1, list.size)
                val lastLiteralType = lastOperandTokens.firstOrNull {it.isLiteral()}?.type
                if (lastLiteralType != null) {
                    memoryRepository.saveMemory(
                        lastLiteralType,
                        OperationMemory(
                            lastOperation = lastOperation,
                            lastOperand = lastOperandTokens.toList()
                        )
                    )
                }
            }
        }
    }

    operator fun invoke(expression: String){
        val tokens = tokenizer.tokenize(expression)
        invoke(tokens)
    }
}