package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

class ParserRegistry(
    private val parsers: List<Parser>
) {
    fun getByName(name: String): Parser? = parsers.find{it.name == name}
    fun getAll(): List<Parser> = parsers
}