package ru.krivenchukartem.universalcalcapp.data.calculator.memory.model

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token

data class OperationMemoryDto(
    val lastOperation: Token,
    val lastOperand: Token
)