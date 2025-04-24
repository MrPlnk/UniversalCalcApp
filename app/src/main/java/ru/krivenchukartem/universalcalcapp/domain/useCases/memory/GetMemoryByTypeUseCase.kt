package ru.krivenchukartem.universalcalcapp.domain.useCases.memory

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.OperationMemory
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.service.TokenTypeExtractor
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import javax.inject.Inject

class GetMemoryByTypeUseCase @Inject constructor(
    private val memoryRepository: OperationMemoryRepository,
    private val tokenTypeExtractor: TokenTypeExtractor
) {
    operator fun invoke(type: Token.Type): OperationMemory?{
        return memoryRepository.getMemoryByType(type)
    }

    operator fun invoke(expression: String): OperationMemory?{
        val types = tokenTypeExtractor.extract(expression)
        return invoke(types.last())
    }
}