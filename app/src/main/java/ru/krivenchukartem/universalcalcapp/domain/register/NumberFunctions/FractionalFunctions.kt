package ru.krivenchukartem.universalcalcapp.domain.register.NumberFunctions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.register.UnaryFunction


object FractionalReciprocal: UnaryFunction<NumberFractional>{
    override val name: String
        get() = "reciprocal"

    override fun apply(input: NumberFractional): NumberFractional {
        return NumberFractional(1, 1) / input
    }
}

