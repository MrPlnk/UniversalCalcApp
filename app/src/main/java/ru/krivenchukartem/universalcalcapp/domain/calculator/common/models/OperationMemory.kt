package ru.krivenchukartem.universalcalcapp.domain.calculator.common.models

import ru.krivenchukartem.universalcalcapp.domain.errors.AppException
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.MemoryException


data class OperationMemory(
    val lastOperation: Token,
    val lastOperand: List<Token>
){
    init {
        validate()
    }

    private fun validate(){
        if (lastOperation.type != Token.Type.OPERATOR){
            throw MemoryException.InvalidTokenTypeForOperatorArgument(lastOperation.token)
        }

        val containsNumber = lastOperand.any { it.isLiteral() }

        if (!containsNumber) {
            val repr = lastOperand.joinToString(" ") { it.token }
            throw MemoryException.InvalidTokenTypeForOperandArgument(repr)
        }
    }
}