package ru.krivenchukartem.universalcalcapp.domain.calculator.processor

import ru.krivenchukartem.universalcalcapp.domain.calculator.register.RegistryProvider
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Token
import java.security.Provider

interface Processor{
    fun evaluate(tokens: List<Token>): Token
}

class RPNProcessor(private val registryProvider: RegistryProvider): Processor {

    private fun selectRegistry(){

    }

    override fun evaluate(tokens: List<Token>): Token {

        val stack = ArrayDeque(listOf<Token>())
        for (token in tokens){
            when (token.type){
                Token.Type.PSYSTEM_LITERAL,
                     Token.Type.COMPLEX_LITERAL,
                         Token.Type.FRACTIONAL_LITERAL -> {
                    stack.addFirst(token)
                }

                Token.Type.OPERATOR -> {

                    if (token.asc == Token.OperatorAssociativity.LEFT){

                    }
                }

                Token.Type.FUNCTION -> TODO()
                else -> Unit
            }
        }
        return stack.first()
    }
}