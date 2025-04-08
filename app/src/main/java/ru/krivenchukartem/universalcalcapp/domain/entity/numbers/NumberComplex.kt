package ru.krivenchukartem.universalcalcapp.domain.entity.numbers

import kotlin.math.*

data class NumberComplex(
    private val re: Double,
    private val im: Double,
) : NumberBase<NumberComplex> {

    override fun toString(): String {
        return when {
            im > 0 -> "$re + ${im}i"
            im < 0 -> "$re - ${abs(im)}i"
            else -> "$re"
        }
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
        require(denominator != 0.0) { "Division by zero in complex number" }

        return NumberComplex(
            (re * other.re + im * other.im) / denominator,
            (im * other.re - re * other.im) / denominator
        )
    }
}
