package ru.krivenchukartem.universalcalcapp.ui.screens.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import ru.krivenchukartem.universalcalcapp.R
import ru.krivenchukartem.universalcalcapp.UniversalCalcTopAppBar
import ru.krivenchukartem.universalcalcapp.ui.navigation.NavigationDestination
import androidx.hilt.navigation.compose.hiltViewModel



object UniversalCalcDestination : NavigationDestination{
    override val route = "universalCalc"
    override val titleRes = R.string.universal_calc_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalCalcScreen(
    viewModel: UniversalCalcViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            UniversalCalcTopAppBar(
                title = stringResource(UniversalCalcDestination.titleRes),
                canNavigateBack = false,
                modifier = modifier
            )
        }
    ) { innerPadding ->
        UniversalCalcBody(
            updateExpressionBar = viewModel::updateState,
            uiState = uiState.value,
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@Composable
fun UniversalCalcBody(
    updateExpressionBar: (String) -> Unit,
    uiState: UniversalCalcState,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        ExpressionBar(
            updateBar = updateExpressionBar,
            barExpression = uiState.expression,
            barResult = uiState.result
        )
    }
}

@Composable
fun ExpressionBar(
    updateBar: (String) -> Unit,
    barExpression: String,
    barResult: String,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        Text(
            text = barResult,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            onValueChange = updateBar,
            value = barExpression,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun UniversalCalcPanelMain(
    viewModel: UniversalCalcViewModel
){
    val digits = listOf(
        "0","1","2","3",
        "4","5","6","7",
        "8","9","A","B",
        "C","D","E","F"
    )
    val digitsMap: Map<String, () -> Unit> = digits.associateWith { digit ->
        {viewModel.updateState(digit)}
    }

    val calcThingsSymbols = listOf(
        "[", "]", "(", ")", "+", "-", "*", "/", ",", "="
    )
    val calcThingsSymbolsMap: Map<String, () -> Unit> = calcThingsSymbols.associateWith { thing ->
        {viewModel.updateState(thing)}
    }

    val deleteButtonsMap: Map<String, () -> Unit> = mapOf(
        "C" to {viewModel.clearExpression()},
        "<" to {viewModel.backSpace()}
    )

    Column {
        CalcActionPanel(4, 4, digitsMap)
    }


}

@Composable
fun CalcActionPanel(
    rows: Int,
    cols: Int,
    mapActions: Map<String, () -> Unit>,
    modifier: Modifier = Modifier,

) {
    val grid: List<List<String>> = mapActions.keys
        .chunked(cols)
        .take(rows)

    Column(modifier) {
        grid.forEach { rowDigits ->
            Row {
                rowDigits.forEach { elem ->
                    Button(onClick = { mapActions[elem]?.invoke() }) {
                        Text(elem)
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun ExpressionBarPreview(){
    ExpressionBar(
        updateBar = {value: String -> Unit},
        barExpression = "[8/9] + square([1/3] + [3/2])",
        barResult = "[432/213]",
    )
}