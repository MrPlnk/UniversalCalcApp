package ru.krivenchukartem.universalcalcapp.domain.calculator.register

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

class UnaryRegistry<T : NumberBase<T>>(
    private val functions: List<UnaryFunction<T>>
) {
    fun getByName(name: String): UnaryFunction<T>? = functions.find { it.name == name }
    fun all(): List<UnaryFunction<T>> = functions
}

class BinaryRegistry<T : NumberBase<T>>(
    private val functions: List<BinaryFunction<T>>
) {
    fun getByName(name: String): BinaryFunction<T>? = functions.find { it.name == name }
    fun all(): List<BinaryFunction<T>> = functions
}
