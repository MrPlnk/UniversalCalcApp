package ru.krivenchukartem.universalcalcapp.domain.errors

sealed class AppException(message: String = "Ошибка работы программы"): RuntimeException(message) {
    sealed class FunctionRegistryExceptions(message: String): AppException(message) {
        class UnmatchedArguments(function: String, expected: Int, get: Int): FunctionRegistryExceptions("Function '$function' expects $expected argument(s), but got $get")
        class UndefinedFunction(function: String): FunctionRegistryExceptions("Неизвестная функция: $function")
    }

    sealed class ParserException(message: String): AppException(message){
        class UnmatchedParenthesis(count: String = "?"): ParserException("Несбалансированные скобки в выражении: $count")
        class SeparatorWithoutArgument(): ParserException("После разделителя функции следуюет закрывающая скобка; Ожидался аргумент")
    }

    sealed class ParserRegistryExceptions(message: String): AppException(message) {
        class CantConvertNumber(literal: String, type: String): ParserRegistryExceptions("Can't convert $literal into $type")
    }

    sealed class ProcessorExceptions(message: String): AppException(message) {
        class UnmatchedArguments(function: String, expected: Int, get: Int): ProcessorExceptions("Function '$function' expects $expected argument(s), but got $get")
        class MissingValue(token: String): ProcessorExceptions("Для токена $token не удалось получить значение")
    }

    sealed class TokenizerExceptions(message: String) : AppException(message) {
        class InvalidToken(token: String) : TokenizerExceptions("Ошибка в формировании токена: $token")
        class UnmatchedParenthesis(count: String): TokenizerExceptions("Несбалансированные скобки в выражении: $count")
        class InvalidCharacter(char: String): TokenizerExceptions("Неизвестный символ: $char")
        class InvalidOperator(char: String): TokenizerExceptions("Неизвестный оператор: $char")
    }

    sealed class MemoryException(message: String): AppException(message){
        class InvalidTokenTypeForOperatorArgument(token: String): MemoryException("Токен $token не является оператором")
        class InvalidTokenTypeForOperandArgument(token: String): MemoryException("Токен $token не является операндом")
    }
}