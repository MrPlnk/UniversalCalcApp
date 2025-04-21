package ru.krivenchukartem.universalcalcapp.data.calculator.memory

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.OperationMemory
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository

class InMemoryOperationMemoryRepository: OperationMemoryRepository {
    private var operationHistory: HashMap<Token.Type, OperationMemory> = hashMapOf()

    override fun getMemoryByType(type: Token.Type): OperationMemory? {
        return operationHistory[type]
    }

    override fun saveMemory(
        type: Token.Type,
        memory: OperationMemory
    ) {
        operationHistory.put(type, memory)
    }

    override fun clearMemory() {
        operationHistory.clear()
    }
}