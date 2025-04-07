package ru.krivenchukartem.universalcalcapp.domain.entity

data class Expression<T: NumberBase<T>>(
    val numbers: List<T> = listOf(),
    val action: String = ""
)