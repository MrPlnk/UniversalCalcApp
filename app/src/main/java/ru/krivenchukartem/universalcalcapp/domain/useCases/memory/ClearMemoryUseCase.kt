package ru.krivenchukartem.universalcalcapp.domain.useCases.memory

import ru.krivenchukartem.universalcalcapp.data.calculator.memory.InMemoryOperationMemoryRepository
import ru.krivenchukartem.universalcalcapp.domain.repositoryInterfaces.OperationMemoryRepository
import javax.inject.Inject

class ClearMemoryUseCase @Inject constructor(
    private val memoryRepository: OperationMemoryRepository
) {
    operator fun invoke(): Result<Unit> = kotlin.runCatching{
        memoryRepository.clearMemory()
    }
}