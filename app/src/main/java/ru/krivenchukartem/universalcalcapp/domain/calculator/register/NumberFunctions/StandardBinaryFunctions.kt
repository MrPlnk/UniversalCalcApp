package ru.krivenchukartem.universalcalcapp.domain.calculator.register.NumberFunctions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.calculator.register.BinaryFunction

class StandardBinaryFunctions<T : NumberBase<T>> {
    fun add() = object : BinaryFunction<T> {
        override val name = "+"
        override fun apply(input1: T, input2: T) = input1 + input2
    }

    fun sub() = object : BinaryFunction<T> {
        override val name = "-"
        override fun apply(input1: T, input2: T) = input1 - input2
    }

    fun mul() = object : BinaryFunction<T> {
        override val name = "*"
        override fun apply(input1: T, input2: T) = input1 * input2
    }

    fun div() = object : BinaryFunction<T> {
        override val name = "/"
        override fun apply(input1: T, input2: T) = input1 / input2
    }

    fun all(): List<BinaryFunction<T>> = listOf(add(), sub(), mul(), div())
}