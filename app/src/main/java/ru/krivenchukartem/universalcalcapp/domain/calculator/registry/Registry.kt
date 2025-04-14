package ru.krivenchukartem.universalcalcapp.domain.calculator.registry

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

class Registry<T : NumberBase<T>>(
    private val functions: List<Function<T>>
) {
    fun getByName(name: String): Function<T>? = functions.find { it.name == name }
    fun all(): List<Function<T>> = functions
}