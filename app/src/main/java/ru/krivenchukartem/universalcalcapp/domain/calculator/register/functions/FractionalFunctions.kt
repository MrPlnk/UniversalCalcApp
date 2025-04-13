package ru.krivenchukartem.universalcalcapp.domain.calculator.register.functions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.calculator.register.Function


object FractionalReciprocal: Function<NumberFractional>{
    override val name: String
        get() = "reciprocal"

    override val arity: Int
        get() = 1

    override fun applyInternal(input: List<NumberFractional>): NumberFractional {
        return NumberFractional(1, 1) / input[0]
    }
}

