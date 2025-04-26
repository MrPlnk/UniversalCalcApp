package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.ParserRegistryProvider
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserRegistryUnitTest {
    @Test
    fun getAll_checkNames(){
        val list = ParserRegistryProvider.parsers.getAll()
        val namesResult = mutableSetOf<String>()
        list.forEach { namesResult.add(it.name) }
        val namesExpected = setOf("pSystem", "fractional", "complex")
        assertEquals(namesExpected, namesResult.toSet())
    }
}