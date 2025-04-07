package ru.krivenchukartem.universalcalcapp.domain.entity

import kotlin.math.abs

data class NumberFractional(
    private var numerator: Int,
    private var divider: Int
) : NumberBase<NumberFractional> {

    init {
        require(divider != 0) { "Знаменатель не может быть 0" }
        if (divider < 0){
            divider = abs(divider)
            numerator *= -1
        }
        shortFractional()
    }

    companion object {
        const val delimiter = "/"
    }

    override fun toString(): String = "$numerator$delimiter$divider"

    override fun plus(other: NumberFractional): NumberFractional =
        NumberFractional(
            numerator * other.divider + divider * other.numerator,
            divider * other.divider
        )

    override fun minus(other: NumberFractional): NumberFractional =
        NumberFractional(
            numerator * other.divider - divider * other.numerator,
            divider * other.divider
        )

    override fun times(other: NumberFractional): NumberFractional =
        NumberFractional(numerator * other.numerator, divider * other.divider)

    override fun div(other: NumberFractional): NumberFractional {
        require(other.numerator != 0) { "Деление на 0 запрещено" }
        return NumberFractional(numerator * other.divider, divider * other.numerator)
    }

    private fun shortFractional() {
        if (numerator == 0) {
            divider = 1
            return
        }

        var a = numerator
        var b = divider

        while (b != 0) {
            val temp = b
            b = a % b
            a = temp
        }

        numerator /= a
        divider /= a
    }
}
