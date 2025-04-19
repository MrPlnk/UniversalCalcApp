package ru.krivenchukartem.universalcalcapp.domain.errors

//sealed class TokenizerExceptions(message: String) : AppException(message) {
//    class InvalidToken(token: String) : TokenizerExceptions("Ошибка в формировании токена: $token")
//    class UnmatchedParenthesis(count: String): TokenizerExceptions("Несбалансированные скобки в выражении: $count")
//    class InvalidCharacter(char: String): TokenizerExceptions("Неизвестный символ: $char")
//    class InvalidOperator(char: String): TokenizerExceptions("Неизвестный оператор: $char")
//}