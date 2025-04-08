package ru.krivenchukartem.universalcalcapp.domain.register

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberComplex
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberPSystem
import ru.krivenchukartem.universalcalcapp.domain.register.NumberFunctions.*

object RegistryProvider {
    val fractionalBinaryRegistry = BinaryRegistry(
        StandardBinaryFunctions<NumberFractional>().all()
    )

    val complexBinaryRegistry = BinaryRegistry(
        StandardBinaryFunctions<NumberComplex>().all()
    )

    val pSystemBinaryRegistry = BinaryRegistry(
        StandardBinaryFunctions<NumberPSystem>().all()
    )

    val fractionalUnaryRegistry = UnaryRegistry(
        StandardUnaryFunctions<NumberFractional>().all() + listOf(FractionalReciprocal)
    )

    val complexUnaryRegistry = UnaryRegistry(
        StandardUnaryFunctions<NumberComplex>().all() + listOf()
    )

    val pSystemUnaryRegistry = UnaryRegistry(
        StandardUnaryFunctions<NumberPSystem>().all() + listOf()
    )
}