package ru.krivenchukartem.universalcalcapp.domain.calculator.register

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

sealed interface Functions<T: NumberBase<T>>{
    val name: String
}

interface UnaryFunction<T : NumberBase<T>>: Functions<T> {
    fun apply(input: T): T
}

interface BinaryFunction<T : NumberBase<T>>: Functions<T>{
    fun apply(input1: T, input2: T): T
}