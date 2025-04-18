package ru.krivenchukartem.universalcalcapp.domain.calculator.parser

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException
import ru.krivenchukartem.universalcalcapp.domain.errors.TokenizerExceptions

interface BaseRPN{
    fun toRPN(tokens: List<Token>): List<Token>
}

class RPN: BaseRPN {
    override fun toRPN(tokens: List<Token>): List<Token> {
        val output = mutableListOf<Token>()
        val stack = ArrayDeque(listOf<Token>())

        tokens.forEach {
            when (it.type){
                Token.Type.COMPLEX_LITERAL,
                Token.Type.PSYSTEM_LITERAL,
                Token.Type.FRACTIONAL_LITERAL ->{
                        output.add(it)
                }

                Token.Type.FUNCTION -> {
                    stack.addFirst(it)
                }

                Token.Type.OPERATOR -> {
                    while (stack.isNotEmpty() && stack.first().type == Token.Type.OPERATOR){
                        val top = stack.first()
                        val p1 = OperatorPrecedence.getPrecedence(top)
                        val p2 = OperatorPrecedence.getPrecedence(it)
                        if ((it.asc == Token.OperatorAssociativity.LEFT && p1 <= p2) ||
                            (it.asc == Token.OperatorAssociativity.RIGHT && p1 < p2)){
                            output += stack.removeFirst()
                            continue
                        }
                        break
                    }
                    stack.addFirst(it)
                }

                Token.Type.L_PARENTHESIS -> {
                    stack.addFirst(it)
                }
                Token.Type.R_PARENTHESIS -> {
                    while (stack.isNotEmpty() && stack.first().type != Token.Type.L_PARENTHESIS){
                        output += stack.removeFirst()
                    }
                    if (stack.isEmpty() || stack.first().type != Token.Type.L_PARENTHESIS){
                        throw TokenizerExceptions.UnmatchedParenthesis("")
                    }
                    stack.removeFirst()
                    if (stack.isNotEmpty() && stack.first().type == Token.Type.FUNCTION) {
                        output += stack.removeFirst()
                    }
                }

                Token.Type.SEPARATOR -> {
                    while (stack.isNotEmpty() && stack.first().type != Token.Type.L_PARENTHESIS){
                        output += stack.removeFirst()
                    }
                    if (stack.isEmpty() || stack.first().type != Token.Type.L_PARENTHESIS){
                        throw AppException()
                    }
                }
            }
        }

        while (stack.isNotEmpty()) {
            val top = stack.removeFirst()
            if (top.type == Token.Type.L_PARENTHESIS || top.type == Token.Type.R_PARENTHESIS) {
                throw TokenizerExceptions.UnmatchedParenthesis("")
            }
            output += top
        }

        return output.toList()
    }
}