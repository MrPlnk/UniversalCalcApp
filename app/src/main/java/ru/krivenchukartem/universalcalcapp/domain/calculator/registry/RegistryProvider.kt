package ru.krivenchukartem.universalcalcapp.domain.calculator.registry

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FractionalReciprocal
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.StandardFunctions
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem

object RegistryProvider {
    val fractionalUnaryRegistry = Registry(
        StandardFunctions<NumberFractional>().all() + listOf(FractionalReciprocal)
    )

    val complexUnaryRegistry = Registry(
        StandardFunctions<NumberComplex>().all() + listOf()
    )

    val pSystemUnaryRegistry = Registry(
        StandardFunctions<NumberPSystem>().all() + listOf()
    )
}