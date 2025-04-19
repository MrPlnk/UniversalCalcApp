package ru.krivenchukartem.universalcalcapp.domain.calculator.common.models

import ru.krivenchukartem.universalcalcapp.domain.errors.AppException
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.MemoryException


data class OperationMemory(
    val lastOperation: Token,
    val lastOperand: Token
){
    init {
        validate()
    }

    private fun validate(){
        if (lastOperation.type != Token.Type.OPERATOR){
            throw MemoryException.InvalidTokenTypeForOperatorArgument(lastOperation.token)
        }
        when (lastOperand.type){
            Token.Type.FRACTIONAL_LITERAL,
                Token.Type.COMPLEX_LITERAL,
                Token.Type.PSYSTEM_LITERAL -> Unit
            else -> throw MemoryException.InvalidTokenTypeForOperandArgument(lastOperand.token)
        }
    }
}