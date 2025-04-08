package ru.krivenchukartem.universalcalcapp.domain.register.NumberFunctions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.register.UnaryFunction

class StandardUnaryFunctions<T: NumberBase<T>> {
    fun square() = object : UnaryFunction<T>{
        override val name: String
            get() = "square"

        override fun apply(input: T): T = input * input
    }

    fun all(): List<UnaryFunction<T>> = listOf(square())
}