package ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.OperationMemory
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token

interface OperationMemoryRepository {
    fun getMemoryByType(type: Token.Type): OperationMemory?
    fun saveMemory(type: Token.Type, memory: OperationMemory)
    fun clearMemory()
}