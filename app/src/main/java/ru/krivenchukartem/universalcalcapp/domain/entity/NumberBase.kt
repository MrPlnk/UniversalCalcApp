package ru.krivenchukartem.universalcalcapp.domain.entity

interface NumberBase<T : NumberBase<T>> {
    override fun toString(): String

    operator fun plus(other: T): T
    operator fun minus(other: T): T
    operator fun times(other: T): T
    operator fun div(other: T): T
}