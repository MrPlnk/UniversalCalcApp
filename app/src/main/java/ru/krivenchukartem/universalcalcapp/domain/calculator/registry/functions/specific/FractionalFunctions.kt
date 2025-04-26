package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.Function
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional


object FractionalReciprocal: Function<NumberFractional> {
    override val name: String
        get() = "reverse"

    override val arity: Int
        get() = 1

    override fun applyInternal(input: List<NumberFractional>): NumberFractional {
        return NumberFractional(1, 1) / input[0]
    }
}


