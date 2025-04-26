package ru.krivenchukartem.universalcalcapp.ui.screens.calculator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.krivenchukartem.universalcalcapp.domain.useCases.SolveUniversalExpressionUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.ClearMemoryUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.GetMemoryByTypeUseCase
import ru.krivenchukartem.universalcalcapp.domain.useCases.memory.SaveMemoryUseCase
import javax.inject.Inject

@HiltViewModel
class UniversalCalcViewModel @Inject constructor(
    private val solveUniversalExpressionUseCase: SolveUniversalExpressionUseCase,
    private val clearMemoryUseCase: ClearMemoryUseCase,
    private val getMemoryByTypeUseCase: GetMemoryByTypeUseCase,
    private val saveMemoryUseCase: SaveMemoryUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<UniversalCalcState> = MutableStateFlow(UniversalCalcState.Idle())
    val uiState: StateFlow<UniversalCalcState> = _uiState.asStateFlow()

    fun memoryClear() {
        viewModelScope.launch {
            clearMemoryUseCase()
        }
    }

    fun memorySave() {
        val expr = _uiState.value.expression
        viewModelScope.launch {
            saveMemoryUseCase(expr)
        }
    }

    fun memoryRecall() {
        val expr = _uiState.value.expression
        viewModelScope.launch {
            getMemoryByTypeUseCase(expr)
                .onSuccess { mem ->
                    mem?.let { memory ->
                        val recallToken = memory.operand.token
                        val recalled = if (expr.isBlank()) recallToken else "$expr[$recallToken]"
                        updateExpressionManually(recalled)
                    }
                }
        }
    }

    fun memoryAdd() {
        var expression = _uiState.value.expression
        val panel = _uiState.value.panel
        viewModelScope.launch {
            getMemoryByTypeUseCase(expression)
                .onSuccess { mem ->
                    mem?.let { memory ->
                        val recallToken = memory.operand.token
                        val recalled = if (expression.isBlank()) "" else recallToken
                        expression = "$expression + [$recalled]"
                    }
                }

            solveUniversalExpressionUseCase(expression)
                .onSuccess{ value ->
                    _uiState.value = UniversalCalcState.Success(
                        expression = value,
                        result = expression,
                        panel = panel
                    )
                }
                .onFailure { error ->
                    _uiState.value = UniversalCalcState.Error(
                        expression = expression,
                        error = error.message ?: "Неизвестная ошибка",
                        panel = panel
                    )
                }
        }
    }

    fun updateExpression(newValue: String) {
        _uiState.update { current ->
            UniversalCalcState.Idle(
                expression = _uiState.value.expression + newValue,
                panel = current.panel
            )
        }
    }

    fun updateExpressionManually(newValue: String){
        _uiState.update { current ->
            UniversalCalcState.Idle(
                expression = newValue,
                panel = current.panel
            )
        }
    }

    fun clearExpression() {
        _uiState.update { current ->
            UniversalCalcState.Idle(
                panel = current.panel
            )
        }
    }

    fun solveExpression() {
        val expression = _uiState.value.expression
        val panel = _uiState.value.panel
        viewModelScope.launch {
            solveUniversalExpressionUseCase(expression)
                .onSuccess{ value ->
                    _uiState.value = UniversalCalcState.Success(
                        expression = value,
                        result = expression,
                        panel = panel
                    )
                }
                .onFailure { error ->
                    _uiState.value = UniversalCalcState.Error(
                        expression = expression,
                        error = error.message ?: "Неизвестная ошибка",
                        panel = panel
                    )
                }
        }
    }

    fun backSpace(){
        if (_uiState.value.expression.isNotEmpty()){
            _uiState.update { current ->
                UniversalCalcState.Idle(
                    expression = current.expression.dropLast(1),
                    panel = current.panel
                )
            }
        }
    }

    fun changePanel(newPanel: Panel){
        if (_uiState.value.panel != newPanel){
            _uiState.update { current ->
                UniversalCalcState.Idle(
                    expression = current.expression,
                    panel = newPanel,
                    result = current.result
                )
            }
        }
    }
}

sealed class UniversalCalcState {
    abstract val expression: String
    abstract val result: String
    abstract val panel: Panel

    data class Idle(
        override val expression: String = "",
        override val result: String = "",
        override val panel: Panel = Panel.NUMBERS
    ) : UniversalCalcState()

    data class Success(
        override val expression: String = "",
        override val result: String = "",
        override val panel: Panel = Panel.NUMBERS

    ) : UniversalCalcState()

    data class Error(
        override val expression: String = "",
        override val result: String = "?",
        override val panel: Panel = Panel.NUMBERS,
        val error: String = ""
    ) : UniversalCalcState()
}

enum class Panel{
    NUMBERS,
    FUNCTIONS
}