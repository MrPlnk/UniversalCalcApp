package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.Function
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex

object ComplexReciprocal: Function<NumberComplex> {
    override val name: String
        get() = "reverse"

    override val arity: Int
        get() = 1

    override fun applyInternal(input: List<NumberComplex>): NumberComplex {
        return NumberComplex(1.0, 0.0) / input[0]
    }
}

