package ru.krivenchukartem.universalcalcapp.domain.entity.numbers

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.extensions.PSystemConverter

data class NumberPSystem(
    private val number: String,
    private val system: Int
) : NumberBase<NumberPSystem> {

    val base: Int
        get() = system

    override fun toString(): String{
        return "[$number$delimiter$system]"
    }

    companion object{
        const val delimiter = ", "
    }

    override fun plus(other: NumberPSystem): NumberPSystem {
        val resultDec = toDecimal() + other.toDecimal()
        return fromDecimal(resultDec, system)
    }

    override fun minus(other: NumberPSystem): NumberPSystem {
        val resultDec = toDecimal() - other.toDecimal()
        return fromDecimal(resultDec, system)
    }

    override fun times(other: NumberPSystem): NumberPSystem {
        val resultDec = toDecimal() * other.toDecimal()
        return fromDecimal(resultDec, system)
    }

    override fun div(other: NumberPSystem): NumberPSystem {
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
