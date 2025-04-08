package ru.krivenchukartem.universalcalcapp.domain.entity

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase

data class Expression<T: NumberBase<T>>(
    val numbers: List<T> = listOf(),
    val action: String = ""
)