package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.specific

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.Function
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem

object PSystemReciprocal: Function<NumberPSystem> {
    override val name: String
        get() = "reverse"

    override val arity: Int
        get() = 1

    override fun applyInternal(input: List<NumberPSystem>): NumberPSystem {
        return NumberPSystem("1", input[0].base) / input[0]
    }
}

