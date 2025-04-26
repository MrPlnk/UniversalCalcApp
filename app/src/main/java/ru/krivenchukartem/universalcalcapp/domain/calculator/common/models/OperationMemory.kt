package ru.krivenchukartem.universalcalcapp.domain.calculator.common.models

import ru.krivenchukartem.universalcalcapp.domain.errors.AppException
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.MemoryException


data class OperationMemory(
    val operand: Token
){
    init {
        validate()
    }

    private fun validate(){
        if (!operand.isLiteral()){
            throw MemoryException.InvalidTokenTypeForOperandArgument(operand.toString())
        }
    }
}