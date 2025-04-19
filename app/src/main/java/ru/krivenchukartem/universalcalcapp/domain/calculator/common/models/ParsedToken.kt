package ru.krivenchukartem.universalcalcapp.domain.calculator.common.models

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.TokenizerExceptions


data class ParsedToken<T: NumberBase<T>> (
    val token: String,
    val type: Type,
    var value: T? = null
){

    enum class Type{
        OPERATOR,      // унарный/бинарный оператор
        LITERAL,
        FUNCTION,      // функция
    }

}