package ru.krivenchukartem.universalcalcapp.domain.register

import ru.krivenchukartem.universalcalcapp.domain.entity.NumberBase

interface UnaryFunction<T : NumberBase<T>> {
    val name: String
    fun apply(input: T): T
}

interface BinaryFunction<T : NumberBase<T>>{
    val name: String
    fun apply(input1: T, input2: T): T
}