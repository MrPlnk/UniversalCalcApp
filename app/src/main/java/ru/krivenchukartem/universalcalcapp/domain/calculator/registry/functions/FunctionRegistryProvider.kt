package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.specific.FractionalReciprocal
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.specific.StandardFunctions
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem

object FunctionRegistryProvider {
    val fractionalFunctionRegistry = FunctionRegistry(
        StandardFunctions<NumberFractional>().all() + listOf(FractionalReciprocal)
    )

    val complexFunctionRegistry = FunctionRegistry(
        StandardFunctions<NumberComplex>().all() + listOf()
    )

    val pSystemFunctionRegistry = FunctionRegistry(
        StandardFunctions<NumberPSystem>().all() + listOf()
    )
}