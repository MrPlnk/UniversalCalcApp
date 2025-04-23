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
            // 1) пользователь ввёл только один токен (например, число или функция) — пробуем подставить в начало память
            tokens.size == 1 && tokens[0].isLiteral() -> {
                repository.getMemoryByType(tokens[0].type)?.let { memory ->
                    list += memory.lastOperation
                    list += memory.lastOperand
                }
            }

            // 2) два токена: [литерал, оператор] — если оператор последний, пробуем добавить к нему последний литерал из memory
            tokens.size == 2 && tokens[1].type == Token.Type.OPERATOR && tokens[0].isLiteral() -> {

                list += tokens[0]

            }
        }

            if (list.size >= 3){
                val lastOpIndex = list.indexOfLast { it.type == Token.Type.OPERATOR }
                if (lastOpIndex != -1 && lastOpIndex < list.size - 1) {
                    val lastOperation = list[lastOpIndex]
                    val lastOperandTokens = list.subList(lastOpIndex + 1, list.size)
                    val lastLiteralType = lastOperandTokens.firstOrNull {it.isLiteral()}?.type
                    if (lastLiteralType != null) {
                        repository.saveMemory(
                            lastLiteralType,
                            OperationMemory(
                                lastOperation = lastOperation,
                                lastOperand = lastOperandTokens.toList()
                            )
                        )
                    }
                }

            }

        return list.toList()
    }
}