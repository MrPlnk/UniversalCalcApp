package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.FunctionRegistryExceptions

interface Function<T : NumberBase<T>> {
    val name: String
    val arity: Int  // -1 для переменного количества аргументов

    fun apply(vararg input: T): T {
        if (arity >= 0 && input.size != arity) {
            throw FunctionRegistryExceptions.UnmatchedArguments(name, arity, input.size)
        }
        return applyInternal(input.toList())
    }

    fun applyInternal(input: List<T>): T
}