package ru.krivenchukartem.universalcalcapp.domain.calculator.register.NumberFunctions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.calculator.register.UnaryFunction


object FractionalReciprocal: UnaryFunction<NumberFractional>{
    override val name: String
        get() = "reciprocal"

    override fun apply(input: NumberFractional): NumberFractional {
        return NumberFractional(1, 1) / input
    }
}

