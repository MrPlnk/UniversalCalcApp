package ru.krivenchukartem.universalcalcapp.domain.errors

open class AppException(message: String = "Ошибка работы программы"): Exception(message) {
}