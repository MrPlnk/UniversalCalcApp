package ru.krivenchukartem.universalcalcapp

import org.junit.Test
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberFractional
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows

class NumberFractionalUnitTest {
    @Test
    fun method_toString(){
        var fractional = 5
        var divider = 6

        var numResult = NumberFractional(fractional, divider).toString()
        var numExpected = "$fractional${NumberFractional.delimiter}$divider"

        assertEquals(numExpected, numResult)
    }

    @Test
    fun fractionReduction(){
        var fractional = 4
        var divider = 6

        var numResult = NumberFractional(fractional, divider).toString()
        var numExpected = "2${NumberFractional.delimiter}3"

        assertEquals(numExpected, numResult)
    }

    @Test
    fun attemptToCreateFractionWithNullableDivider(){
        assertThrows(IllegalArgumentException::class.java){
            NumberFractional(1, 0)
        }
    }

    @Test
    fun createNegativeFraction_negativeFractional(){
        var fractional1 = -5
        var divider1 = 6
        var result = NumberFractional(fractional1, divider1).toString()
        var expected = "$fractional1${NumberFractional.delimiter}$divider1"
        assertEquals(expected, result)
    }

    @Test
    fun createNegativeFraction_negativeDivider(){
        var fractional1 = 5
        var divider1 = -6
        var result = NumberFractional(fractional1, divider1).toString()
        var expected = "${-fractional1}${NumberFractional.delimiter}${-divider1}"
        assertEquals(expected, result)
    }

    @Test
    fun operator_plus(){
        var fractional1 = 4
        var divider1 = 6

        var fractional2 = 1
        var divider2 = 2

        var fractional = 7
        var divider = 6

        var num1 = NumberFractional(fractional1, divider1)
        var num2 = NumberFractional(fractional2, divider2)
        var result = (num1 + num2).toString()
        var numExpected = "$fractional${NumberFractional.delimiter}$divider"

        assertEquals(numExpected, result)
    }

    @Test
    fun operator_div(){
        var fractional1 = 4
        var divider1 = 6

        var fractional2 = 1
        var divider2 = 2

        var fractional = 4
        var divider = 3

        var num1 = NumberFractional(fractional1, divider1)
        var num2 = NumberFractional(fractional2, divider2)
        var result = (num1 / num2).toString()
        var numExpected = "$fractional${NumberFractional.delimiter}$divider"

        assertEquals(numExpected, result)
    }
}