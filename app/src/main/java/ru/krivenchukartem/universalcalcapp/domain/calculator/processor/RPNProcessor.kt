package ru.krivenchukartem.universalcalcapp.domain.calculator.processor

import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.ParsedToken
import ru.krivenchukartem.universalcalcapp.domain.calculator.registry.functions.FunctionRegistry
import ru.krivenchukartem.universalcalcapp.domain.calculator.common.models.Token
import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.FunctionRegistryExceptions
import ru.krivenchukartem.universalcalcapp.domain.errors.AppException.ProcessorExceptions


class RPNProcessor<T: NumberBase<T>>(private val functionRegistry: FunctionRegistry<T>) {

    fun evaluate(tokens: List<ParsedToken<T>>): ParsedToken<T> {
        val stack = ArrayDeque(listOf<ParsedToken<T>>())
        for (token in tokens){
            when (token.type){
                ParsedToken.Type.LITERAL -> {
                    stack.addFirst(token)
                }

                ParsedToken.Type.OPERATOR,
                ParsedToken.Type.FUNCTION-> {
                    val function = functionRegistry.getByName(token.token)
                        ?: throw FunctionRegistryExceptions.UndefinedFunction(token.token)

                    if (function.arity > stack.size) {
                        throw ProcessorExceptions.UnmatchedArguments(function.name, function.arity, stack.size)
                    }

                    val args = (1..function.arity).map {
                        stack.removeFirstOrNull()?.value
                            ?: throw ProcessorExceptions.MissingValue(token.token)
                    }.reversed()

                    val result = function.applyInternal(args)
                    stack.addFirst(
                        ParsedToken(
                            token = result.toString(),
                            type = ParsedToken.Type.LITERAL,
                            value = result
                        )
                    )
                }
            }
        }
        return stack.first()
    }
}