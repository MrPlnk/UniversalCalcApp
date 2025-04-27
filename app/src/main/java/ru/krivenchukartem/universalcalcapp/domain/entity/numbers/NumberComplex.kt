package ru.krivenchukartem.universalcalcapp.domain.entity.numbers

import kotlin.math.*

data class NumberComplex(
    private val re: Double,
    private val im: Double,
) : NumberBase<NumberComplex> {

    override fun toString(): String {
        return when {
            im > 0 -> "[$re$delimiterPlus${im}i]"
            im < 0 -> "[$re$delimiterMinus${abs(im)}i]"
            else -> "[$re]"
        }
    }

    companion object{
        const val delimiterPlus = " + "
        const val delimiterMinus = " - "
    }

    override fun plus(other: NumberComplex): NumberComplex {
        return NumberComplex(re + other.re, im + other.im)
    }

    override fun minus(other: NumberComplex): NumberComplex {
        return NumberComplex(re - other.re, im - other.im)
    }

    override fun times(other: NumberComplex): NumberComplex {
        return NumberComplex(
            re * other.re - im * other.im,
            re * other.im + im * other.re
        )
    }

    override fun div(other: NumberComplex): NumberComplex {
        val denominator = other.re * other.re + other.im * other.im
        require(denominator != 0.0) { "Деление на ноль невозможно" }

        return NumberComplex(
            (re * other.re + im * other.im) / denominator,
            (im * other.re - re * other.im) / denominator
        )
    }
}
