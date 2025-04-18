package ru.krivenchukartem.universalcalcapp.domain.calculator.registry.parsers

class NameParserRegistry(
    private val parsers: List<Parser<*>>
) {
    fun getByName(name: String): Parser<*>? = parsers.find{it.name == name}
    fun getAll(): List<Parser<*>> = parsers
}