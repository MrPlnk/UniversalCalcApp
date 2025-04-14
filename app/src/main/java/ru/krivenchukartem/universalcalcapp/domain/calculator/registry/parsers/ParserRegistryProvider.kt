package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.ComplexParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.FractionalParser
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers.specific.PSystemParser

object ParserRegistryProvider {
    val parsers = ParserRegistry(
        listOf(ComplexParser, FractionalParser, PSystemParser)
    )
}