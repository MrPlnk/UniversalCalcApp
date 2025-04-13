package ru.krivenchukartem.universalcalcapp.domain.calculator.register

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.errors.RegistryExceptions

interface Function<T : NumberBase<T>> {
    val name: String
    val arity: Int  // -1 для переменного количества аргументов

    fun apply(vararg input: T): T {
        if (arity >= 0 && input.size != arity) {
            throw RegistryExceptions.UnmatchedArguments(name, arity, input.size)
        }
        return applyInternal(input.toList())
    }

    fun applyInternal(input: List<T>): T
}