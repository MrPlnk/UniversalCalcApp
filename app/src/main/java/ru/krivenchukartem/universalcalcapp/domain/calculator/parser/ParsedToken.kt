package ru.krivenchukartem.universalcalcapp.domain.calculator.parser

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.errors.TokenizerExceptions

data class ParsedToken<T: NumberBase<T>> (
    val token: String,
    val type: Type,
    val asc: OperatorAssociativity,
    var value: T? = null
){
    init {
        validate()
    }

    private fun validate(){
        if (type == Type.OPERATOR && asc == OperatorAssociativity.NONE){
            throw TokenizerExceptions.InvalidToken(this.toString())
        }
        else if (type != Type.OPERATOR && asc != OperatorAssociativity.NONE){
            throw TokenizerExceptions.InvalidToken(this.toString())
        }
    }

    enum class Type{
        OPERATOR,      // унарный/бинарный оператор
        L_PARENTHESIS, // открывающая скобка
        R_PARENTHESIS, // закрывающая скобка
        COMPLEX_LITERAL, // комплексное число
        FRACTIONAL_LITERAL, // простая дробь
        PSYSTEM_LITERAL, // число в системе счисления
        FUNCTION,      // функция
        SEPARATOR      // разделитель аргументов функции
    }

    enum class OperatorAssociativity{
        NONE,  // токен - не оператор
        RIGHT, // правоассоциативный
        LEFT   // левоассоциативный
    }
}
