package ru.krivenchukartem.universalcalcapp.ui.screens.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    val digits = listOf(
        "0","1","2","3",
        "4","5","6","7",
        "8","9","A","B",
        "C","D","E","F"
    )
    val digitsMap: Map<String, () -> Unit> = digits.associateWith { digit ->
        {viewModel.updateExpression(digit)}
    }

    val calcThingsSymbolsMap: Map<String, () -> Unit> = mapOf(
        "[" to {viewModel.updateExpression("[")},
        "]" to {viewModel.updateExpression("]")},
        "(" to {viewModel.updateExpression("(")},
        ")" to {viewModel.updateExpression(")")},
        "+" to {viewModel.updateExpression(" + ")},
        "-" to {viewModel.updateExpression(" - ")},
        "*" to {viewModel.updateExpression(" * ")},
        "/" to {viewModel.updateExpression(" / ")},
        "," to {viewModel.updateExpression(", ")},
        "." to {viewModel.updateExpression(".")},
    )

    val usefulButtonsMap: Map<String, () -> Unit> = mapOf(
        "C" to {viewModel.clearExpression()},
        "<" to {viewModel.backSpace()},
        "=" to {viewModel.solveExpression()}
    )

    val memoryActionsMap: Map<String, () -> Unit> = mapOf(
        "MC" to {},
        "MS" to {},
        "MR" to {},
        "M+" to {}
    )

    val functionsMap: MutableMap<String, () -> Unit> = mutableMapOf(
        "sqr" to {viewModel.updateExpression("square(")},
        "rev" to {viewModel.updateExpression("reverse(")}
    )
    for (i in functionsMap.size..16){
        functionsMap["$i"] = {}
    }

    val mainPanelMap: Map<String, Map<String, () -> Unit>> = mapOf(
        "digitsMap" to digitsMap,
        "calcThingsSymbolsMap" to calcThingsSymbolsMap,
        "usefulButtonsMap" to usefulButtonsMap,
        "memoryActionsMap" to memoryActionsMap,
        "functionsMap" to functionsMap,
    )

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
            updateExpressionBar = viewModel::updateExpressionManually,
            changeCurrentPanel = viewModel::changePanel,
            uiState = uiState.value,
            mainPanelMap = mainPanelMap,
            modifier = Modifier.padding(innerPadding)
        )
    }

}

@Composable
fun UniversalCalcBody(
    updateExpressionBar: (String) -> Unit,
    uiState: UniversalCalcState,
    changeCurrentPanel: (Panel) -> Unit,
    mainPanelMap: Map<String, Map<String, () -> Unit>>,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxSize()
    ) {
        if (uiState is UniversalCalcState.Error){
            ShowError(uiState.error)
        }
        Spacer(Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ExpressionBar(
                updateBar = updateExpressionBar,
                barExpression = uiState.expression,
                barResult = uiState.result
            )
            UniversalCalcPanel(
                panelMap =  mainPanelMap,
                changePanel = changeCurrentPanel,
                currentPanel = uiState.panel,
                modifier = Modifier.fillMaxWidth()
            )
        }
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
fun UniversalCalcPanel(
    panelMap: Map<String, Map<String, () -> Unit>>,
    changePanel: (Panel) -> Unit,
    currentPanel: Panel,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PanelMenu(
            changePanel = changePanel
        )
        CalcActionPanel(
            panelMap.getValue("memoryActionsMap"), 1, 4,
            buttonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))) {
            Column() {
                when (currentPanel){
                    Panel.NUMBERS ->
                        CalcActionPanel(
                            panelMap.getValue("digitsMap"), 4, 4,
                            buttonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        )

                    Panel.FUNCTIONS ->
                        CalcActionPanel(
                            panelMap.getValue("functionsMap"), 4, 4,
                            buttonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
                        )
                }

                CalcActionPanel(
                    panelMap.getValue("usefulButtonsMap"), 1, 3,
                    buttonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
                )
            }
            CalcActionPanel(
                panelMap.getValue("calcThingsSymbolsMap"), 5, 2,
                buttonColors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
            )
        }
    }
}

@Composable
fun PanelMenu(
    changePanel: (Panel) -> Unit,
    modifier: Modifier = Modifier
){
    Column(modifier) {
        HorizontalDivider()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            OutlinedButton(onClick = {changePanel(Panel.NUMBERS)}) {
                Text(text = "123")
            }
            Spacer(Modifier.padding(dimensionResource(R.dimen.padding_small)))
            OutlinedButton(onClick = {changePanel(Panel.FUNCTIONS)}) {
                Text(text = "fun")
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.onSecondaryContainer)
    }
}


@Composable
fun CalcActionPanel(
    mapActions: Map<String, () -> Unit>,
    rows: Int,
    cols: Int,
    modifier: Modifier = Modifier,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    val grid: List<List<String>> = mapActions.keys
        .chunked(cols)
        .take(rows)

    Column(
        modifier = modifier
    ) {
        grid.forEach { rowDigits ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
            ) {
                rowDigits.forEach { elem ->
                    Button(
                        onClick = { mapActions[elem]?.invoke() },
                        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
                        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_small)),
                        modifier = Modifier.weight(1f, fill = false),
                        colors = buttonColors
                    ) {
                        Text(elem)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowError(
    error: String,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier.padding(dimensionResource(R.dimen.padding_medium)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer
        ),
    ) {
        Text(
            text = error,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Preview
@Composable
fun PanelMenuPreview(){
    PanelMenu(
        changePanel = {}
    )
}

@Preview
@Composable
fun ShowErrorPreview(){
    ShowError(
        error = "- Фигня твой код.\n" +
                "- Да.",
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun UniversalCalcPanelMainPreview(){
    val digits = listOf(
        "0","1","2","3",
        "4","5","6","7",
        "8","9","A","B",
        "C","D","E","F"
    )
    val digitsMap: Map<String, () -> Unit> = digits.associateWith { digit ->
        {}
    }

    val calcThingsSymbols = listOf(
        "[", "]", "(", ")", "+", "-", "*", "/", ",", "."
    )
    val calcThingsSymbolsMap: Map<String, () -> Unit> = calcThingsSymbols.associateWith { thing ->
        {}
    }

    val usefulButtonsMap: Map<String, () -> Unit> = mapOf(
        "C" to {},
        "<" to {},
        "=" to {}
    )

    val memoryActionsMap: Map<String, () -> Unit> = mapOf(
        "MC" to {},
        "MS" to {},
        "MR" to {},
        "M+" to {}
    )

    val mainPanelMap: Map<String, Map<String, () -> Unit>> = mapOf(
        "digitsMap" to digitsMap,
        "calcThingsSymbolsMap" to calcThingsSymbolsMap,
        "usefulButtonsMap" to usefulButtonsMap,
        "memoryActionsMap" to memoryActionsMap
    )
    UniversalCalcPanel(
        panelMap =  mainPanelMap,
        currentPanel = Panel.NUMBERS,
        changePanel = {},
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
fun UniversalCalcBodyPreview(){
    val digits = listOf(
        "0","1","2","3",
        "4","5","6","7",
        "8","9","A","B",
        "C","D","E","F"
    )
    val digitsMap: Map<String, () -> Unit> = digits.associateWith { digit ->
        {}
    }

    val calcThingsSymbols = listOf(
        "[", "]", "(", ")", "+", "-", "*", "/", ",", "."
    )
    val calcThingsSymbolsMap: Map<String, () -> Unit> = calcThingsSymbols.associateWith { thing ->
        {}
    }

    val usefulButtonsMap: Map<String, () -> Unit> = mapOf(
        "C" to {},
        "<" to {},
        "=" to {}
    )

    val memoryActionsMap: Map<String, () -> Unit> = mapOf(
        "MC" to {},
        "MS" to {},
        "MR" to {},
        "M+" to {}
    )

    val mainPanelMap: Map<String, Map<String, () -> Unit>> = mapOf(
        "digitsMap" to digitsMap,
        "calcThingsSymbolsMap" to calcThingsSymbolsMap,
        "usefulButtonsMap" to usefulButtonsMap,
        "memoryActionsMap" to memoryActionsMap
    )
    UniversalCalcBody(
        updateExpressionBar = { value: String -> },
        uiState = UniversalCalcState.Success(
            expression = "[8/9] + square([1/3] + [3/2])",
            result = "[432/213]",
        ),
        mainPanelMap = mainPanelMap,
        changeCurrentPanel = {},
        modifier = Modifier
    )
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