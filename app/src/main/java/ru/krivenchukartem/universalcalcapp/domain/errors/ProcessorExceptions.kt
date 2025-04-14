package ru.krivenchukartem.universalcalcapp.domain.errors

sealed class ProcessorExceptions(message: String): Exception(message) {
    class UnmatchedArguments(function: String, expected: Int, get: Int): RegistryExceptions("Function '$function' expects $expected argument(s), but got $get")

}