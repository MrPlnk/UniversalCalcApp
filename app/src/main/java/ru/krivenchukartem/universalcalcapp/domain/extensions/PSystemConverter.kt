package ru.krivenchukartem.universalcalcapp.domain.extensions

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow

object PSystemConverter {
    private val digitToString = mapOf(
        0 to "0", 1 to "1", 2 to "2", 3 to "3", 4 to "4",
        5 to "5", 6 to "6", 7 to "7", 8 to "8", 9 to "9",
        10 to "A", 11 to "B", 12 to "C", 13 to "D", 14 to "E", 15 to "F"
    )

    private val stringToDigit = digitToString.entries.associate { (k, v) -> v to k }

    fun fromDecimal(n: Double, p: Int, precision: Int = 5): String {
        var num = abs(n)
        val intPart = floor(num).toInt()
        val fracPart = num - intPart

        val intConverted = intToP(intPart, p)
        val fracConverted = fracToP(fracPart, p, precision)

        val result = if (fracConverted.isNotEmpty()) "$intConverted.$fracConverted" else intConverted
        return if (n < 0) "-$result" else result
    }

    fun toDecimal(number: String, p: Int): Double {
        var num = number
        val isNegative = num.startsWith("-")
        if (isNegative) num = num.substring(1)

        val parts = num.split(".")
        val intPart = parts[0]
        val fracPart = if (parts.size > 1) parts[1] else ""

        val intResult = intPart.reversed().mapIndexed { index, c ->
            stringToDigit[c.toString()]!! * p.toDouble().pow(index.toDouble())
        }.sum()

        val fracResult = fracPart.mapIndexed { index, c ->
            stringToDigit[c.toString()]!! * p.toDouble().pow(-(index + 1).toDouble())
        }.sum()

        val result = intResult + fracResult
        return if (isNegative) -result else result
    }

    private fun intToP(n: Int, p: Int): String {
        var num = n
        if (num == 0) return "0"
        var result = ""
        while (num > 0) {
            result = digitToString[num % p] + result
            num /= p
        }
        return result
    }

    private fun fracToP(n: Double, p: Int, precision: Int): String {
        var num = n
        var result = ""
        var prec = precision
        while (prec > 0 && num != 0.0) {
            num *= p
            val intPart = floor(num).toInt()
            result += digitToString[intPart]
            num -= intPart
            prec--
        }
        return result.trimEnd('0')
    }
}