package ru.krivenchukartem.universalcalcapp.domain.useCases

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.OperationMemory
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.isLiteral
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.SaveMemoryUseCase
import javax.inject.Inject

class ExtendTokenExpressionUseCase @Inject constructor(
    private val repository: OperationMemoryRepository,
    private val saveMemoryUseCase: SaveMemoryUseCase
) {
    operator fun invoke(tokens: List<Token>): List<Token>{
        val list = tokens.toMutableList()
        when {
            tokens.size == 1 && tokens[0].isLiteral() -> {
                repository.getMemoryByType(tokens[0].type)?.let { memory ->
                    list += memory.lastOperation
                    list += memory.lastOperand
                }
            }

            tokens.size == 2 && tokens[1].type == Token.Type.OPERATOR && tokens[0].isLiteral() -> {
                list += tokens[0]
            }
        }

        saveMemoryUseCase(list)
        return list.toList()
    }
}