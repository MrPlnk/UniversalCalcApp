package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.Function

class StandardFunctions<T : NumberBase<T>> {
    fun add() = object : Function<T> {
        override val name = "+"
        override val arity: Int
            get() = 2
        override fun applyInternal(input: List<T>): T = input[0] + input[1]
    }

    fun sub() = object : Function<T> {
        override val name = "-"
        override val arity: Int
            get() = 2
        override fun applyInternal(input: List<T>): T = input[0] - input[1]
    }

    fun mul() = object : Function<T> {
        override val name = "*"
        override val arity: Int
            get() = 2
        override fun applyInternal(input: List<T>): T = input[0] * input[1]
    }

    fun div() = object : Function<T> {
        override val name = "/"
        override val arity: Int
            get() = 2
        override fun applyInternal(input: List<T>): T = input[0] / input[1]
    }

    fun square() = object : Function<T>{
        override val name: String
            get() = "square"
        override val arity: Int
            get() = 1

        override fun applyInternal(input: List<T>): T = input[0] * input[0]
    }

    fun all(): List<Function<T>> = listOf(add(), sub(), mul(), div(), square())
}