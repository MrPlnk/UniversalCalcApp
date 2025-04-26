package ru.krivenchukartem.universalcalcapp.domain.useCases

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.OperationMemory
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.isLiteral
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenTypeExtractor
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.SaveMemoryUseCase
import javax.inject.Inject

class ExtendTokenExpressionUseCase @Inject constructor(
    private val tokenTypeExtractor: TokenTypeExtractor
) {
    operator fun invoke(tokens: List<Token>): Result<List<Token>> = runCatching{
        val list = tokens.toMutableList()
        when {
            tokenTypeExtractor.extractLiteralTypes(tokens).size == 1 &&
                    tokens.last().type == Token.Type.OPERATOR ->{
                val allButLast = tokens.dropLast(1)
                list.addAll(allButLast)
            }
        }
        list.toList()
    }
}