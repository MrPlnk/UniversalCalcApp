package ru.krivenchukartem.universalcalcapp.ui.screens.calculator

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.krivenchukartem.universalcalcapp.domain.useCases.SolveUniversalExpressionUseCase
import javax.inject.Inject

@HiltViewModel
class UniversalCalcViewModel @Inject constructor(
    private val solveUniversalExpressionUseCase: SolveUniversalExpressionUseCase
): ViewModel() {
//    private val _uiState = MutableStateFlow(UniversalCalcState())
//    val uiState: StateFlow<UniversalCalcState> = _uiState.asStateFlow()
//
//    fun updateState(newValue: String) {
//        _uiState.update { current ->
//            current.copy(expression = current.expression + newValue)
//        }
//    }
//
//    fun clearExpression() {
//        _uiState.update { current ->
//            current.copy(expression = "")
//        }
//    }
//
//    fun solveExpression() {
//        val result = solveUniversalExpressionUseCase(_uiState.value.expression)
//        _uiState.update { it.copy(result = result) }
//    }
//
//    fun backSpace(){
//        _uiState.update { current ->
//            current.copy(expression = current.expression.substring(0, current.expression.length - 2))
//        }
//    }
}

sealed class UniversalCalcState {
    data class Success(
        val expression: String = "",
        val result: String = ""
    ) : UniversalCalcState()
    data class Error(
        val message: String = ""
    ) : UniversalCalcState()
}