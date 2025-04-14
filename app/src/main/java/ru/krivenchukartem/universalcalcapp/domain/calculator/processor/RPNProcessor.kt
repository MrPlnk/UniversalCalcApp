package ru.krivenchukartem.universalcalcapp.domain.calculator.processor

import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.Registry
import ru.krivenchukartem.universalcalcapp.domain.calculator.tokenizer.Token
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.errors.ProcessorExceptions
import ru.krivenchukartem.universalcalcapp.domain.errors.RegistryExceptions

interface Processor{
    fun evaluate(tokens: List<Token>): Token
}

class RPNProcessor<T: NumberBase<T>>(private val registry: Registry<T>): Processor {

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
                    val function = registry.getByName(token.token)
                    if (function == null){
                        throw RegistryExceptions.UndefinedFunction(token.token)
                    }
                    if (function.arity > stack.size){
                        throw ProcessorExceptions.UnmatchedArguments(function.name, function.arity, stack.size)
                    }
                    val list = mutableListOf<T>()
                    for (i in 0 until function.arity){
                        list.add(stack.removeFirst())
                    }
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