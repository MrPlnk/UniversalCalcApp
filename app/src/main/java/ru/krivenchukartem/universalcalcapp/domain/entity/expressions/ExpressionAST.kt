package ru.krivenchukartem.universalcalcapp.domain.entity.expressions

import ru.krivenchukartem.universalcalcapp.domain.entity.numbers.NumberBase
import ru.krivenchukartem.universalcalcapp.domain.calculator.register.BinaryFunction
import ru.krivenchukartem.universalcalcapp.domain.calculator.register.UnaryFunction


/* Сущность для AST (abstract syntax tree) */
sealed class ExpressionAST<T : NumberBase<T>> {
    data class Value<T : NumberBase<T>>(val value: T): ExpressionAST<T>()
    data class Unary<T : NumberBase<T>>(val function: UnaryFunction<T>, val arg: ExpressionAST<T>): ExpressionAST<T>()
    data class Binary<T : NumberBase<T>>(val function: BinaryFunction<T>, val left: ExpressionAST<T>, val right: ExpressionAST<T>): ExpressionAST<T>()
}