package ru.krivenchukartem.universalcalcapp.domain.entity

import ru.krivenchukartem.universalcalcapp.domain.extensions.PSystemConverter

data class NumberPSystem(
    private val number: String,
    private val system: Int
) : NumberBase<NumberPSystem> {

    override fun toString(): String = number

    override fun plus(other: NumberPSystem): NumberPSystem {
        require(system == other.system) { "Операции возможны только в одинаковых системах" }

        val resultDec = toDecimal() + other.toDecimal()
        return fromDecimal(resultDec, system)
    }

    override fun minus(other: NumberPSystem): NumberPSystem {
        require(system == other.system) { "Операции возможны только в одинаковых системах" }

        val resultDec = toDecimal() - other.toDecimal()
        return fromDecimal(resultDec, system)
    }

    override fun times(other: NumberPSystem): NumberPSystem {
        require(system == other.system) { "Операции возможны только в одинаковых системах" }

        val resultDec = toDecimal() * other.toDecimal()
        return fromDecimal(resultDec, system)
    }

    override fun div(other: NumberPSystem): NumberPSystem {
        require(system == other.system) { "Операции возможны только в одинаковых системах" }
        require(other.toDecimal() != 0.0) { "Деление на ноль невозможно" }

        val resultDec = toDecimal() / other.toDecimal()
        return fromDecimal(resultDec, system)
    }

    private fun toDecimal(): Double {
        return PSystemConverter.toDecimal(number, system)
    }

    private fun fromDecimal(n: Double, system: Int): NumberPSystem {
        return NumberPSystem(PSystemConverter.fromDecimal(n, system), system)
    }
}
