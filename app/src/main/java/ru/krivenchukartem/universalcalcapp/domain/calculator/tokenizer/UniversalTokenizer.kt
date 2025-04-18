package ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.errors.TokenizerExceptions

enum class States {
    S0, // Начальное состояние: ожидание первого символа токена
    S1, // Числовой литерал: дробное, комплексное или p-ичное (например, "[123, 10]")
    S2, // Идентификатор: для имени функции или переменной (например, "square")
    S3, // Оператор: например, +-/*
    S4, // Скобка: для порядка выполнения операций
}

interface Tokenizer {
    fun tokenize(expression: String): List<Token>
}

class UniversalTokenizer : Tokenizer {

    private val tokens = mutableListOf<Token>()
    private var buffer = StringBuilder()
    private var state = States.S0
    private var idx = 0
    private var parenthesisSyn = 0
    private lateinit var expression: String

    override fun tokenize(expression: String): List<Token> {
        this.expression = expression
        tokens.clear()
        buffer.clear()
        state = States.S0
        idx = 0
        parenthesisSyn = 0

        while (idx < expression.length) {
            val c = expression[idx]
            processChar(c)
        }

        addTokenAndClearBuffer()

        if (parenthesisSyn != 0) {
            throw TokenizerExceptions.UnmatchedParenthesis(parenthesisSyn.toString())
        }

        return tokens.toList()
    }

    private fun processChar(c: Char) {
        when (state){
            States.S0 -> {
                if (c in "[]"){
                    state = States.S1
                }
                else if (c.isLetter()){
                    state = States.S2
                }
                else if (c in "+-/*"){
                    state = States.S3
                }
                else if (c in "()"){
                    state = States.S4
                }
                else if (c.isWhitespace()){
                    idx += 1
                }
                else{
                    throw TokenizerExceptions.InvalidCharacter(c.toString())
                }

            }

            States.S1 -> {
                if (c == '['){
                    Unit
                }
                else if (c == ']'){
                    addTokenAndClearBuffer()
                    state = States.S0
                }
                else{
                    buffer.append(c)
                }
                idx += 1
            }

            States.S2 -> {
                if (c.isLetter()){
                    buffer.append(c)
                    idx += 1
                }
                else{
                    addTokenAndClearBuffer()
                    state = States.S0
                }
            }

            States.S3 -> {
                buffer.append(c)
                addTokenAndClearBuffer()
                state = States.S0
                idx += 1
            }

            States.S4 -> {
                buffer.append(c)
                addTokenAndClearBuffer()
                state = States.S0
                idx += 1
            }
        }
    }

    private fun addToken(raw: String) {
        val (type, asc) = when {
            raw in listOf("+", "-", "*", "/") -> Token.Type.OPERATOR to Token.OperatorAssociativity.LEFT
            raw == "(" -> Token.Type.L_PARENTHESIS to Token.OperatorAssociativity.NONE
            raw == ")" -> Token.Type.R_PARENTHESIS to Token.OperatorAssociativity.NONE
            raw == "," -> Token.Type.SEPARATOR to Token.OperatorAssociativity.NONE
            raw.firstOrNull()?.isLetter() == true -> Token.Type.FUNCTION to Token.OperatorAssociativity.NONE
            else -> determineLiteralType(raw) to Token.OperatorAssociativity.NONE
        }

        tokens.add(Token(raw, type, asc))
    }

    private fun addTokenAndClearBuffer() {
        if (buffer.isNotEmpty()) {
            addToken(buffer.toString())
            buffer.clear()
        }
    }

    // Вспомогательная функция: определяет тип числового литерала.
    // В данном простом примере:
    // - Если строка содержит символ "i" → комплексное число.
    // - Если содержит "/" → простая дробь.
    // - Если содержит "," → число в произвольной системе счисления.
    // В остальных случаях считаем литералом дробного числа.
    private fun determineLiteralType(literal: String): Token.Type {
        return when {
            literal.contains("i") -> Token.Type.COMPLEX_LITERAL
            literal.contains("/") -> Token.Type.FRACTIONAL_LITERAL
            literal.contains(",") -> Token.Type.PSYSTEM_LITERAL
            else -> Token.Type.FRACTIONAL_LITERAL
        }
    }
}
