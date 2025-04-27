package ru.krivenchukartem.universalcalcapp.ui.screens.calculator

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.krivenchukartem.universalcalcapp.R
import ru.krivenchukartem.universalcalcapp.UniversalCalcTopAppBar
import ru.krivenchukartem.universalcalcapp.ui.navigation.NavigationDestination

object UniversalCalcInfoDestination: NavigationDestination{
    override val route: String = "info"
    override val titleRes: Int = R.string.info_screen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalCalcInfoScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UniversalCalcInfoViewModel = hiltViewModel(),
) {
    val currentPage = viewModel.currentPage.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            UniversalCalcTopAppBar(
                title = stringResource(UniversalCalcInfoDestination.titleRes),
                canNavigateBack = true,
                navigateBack = navigateBack,
            )
        }
    ) { innerPadding ->
        UniversalCalcInfoBody(
            currentPage = currentPage.value,
            onPageChanged = viewModel::onPageChanged,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun UniversalCalcInfoBody(
    currentPage: Int,
    onPageChanged: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    val tabs = 4

//        listOf(
//        R.string.info_tab_general,
//        R.string.info_tab_number_types,
//        R.string.info_tab_functions,
//        R.string.info_tab_memory,
//    )

    val pagerState = rememberPagerState(
        initialPage = currentPage,
        pageCount = {tabs}
    )

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest { onPageChanged(it) }
    }

    Column(modifier = modifier.fillMaxSize()) {
        Spacer(Modifier.height(dimensionResource(R.dimen.padding_small)))
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
        ) { page ->
            when (page) {
                0 -> InfoGeneralPage()
                1 -> InfoDataTypesPage()
                2 -> InfoFunctionsPage()
                3 -> InfoMemoryPage()
                else -> Text("Неизвестная страница") // на всякий случай
            }
        }
        Text(
            text = "${currentPage + 1} из $tabs",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
private fun InfoDataTypesPage() {
    Column {
        Text(
            text = stringResource(R.string.info_tab_number_types),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(12.dp))

        DataTypeItem(
            label = "Комплексные",
            example = "[Re ± Im]"
        )
        DataTypeItem(
            label = "Дробные",
            example = "[Числитель / Знаменатель]"
        )
        DataTypeItem(
            label = "Системы счисления",
            example = "[Число, Основание]"
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Числа должны записываться как в примере!",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun InfoFunctionsPage() {
    Column {
        Text(
            text = stringResource(R.string.info_tab_functions),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(12.dp))
        DataTypeItem(
            label = "square()",
            example = "Возводит число в квадрат"
        )
        DataTypeItem(
            label = "reverse()",
            example = "Находит обратное число"
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Поддерживаемые функции зависят от типа числа!",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun InfoGeneralPage() {
    Column{
        Text(
            text = stringResource(R.string.info_tab_general),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(12.dp))

        val bulletItems = listOf(
            "Поддерживает разные типы чисел",
            "Соблюдает приоритет операций",
            "Имеет поддержку памяти",
            "В сущности бесполезен"
        )
        bulletItems.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "•",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = item,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Дополнительные пояснения
        Text(
            text = "Одновременно калькулятор может поддерживать только один тип чисел",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "*Приведение типов будет доступно позже",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun InfoMemoryPage(){
    Column {
        Text(
            text = stringResource(R.string.info_tab_memory),
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(Modifier.height(6.dp))
        Image(
            painter = painterResource(R.drawable.memory_buttons),
            contentDescription = stringResource(R.string.memory_buttons),
            modifier = Modifier.fillMaxWidth().height(39.dp)
        )
        Spacer(Modifier.height(6.dp))
        DataTypeItem(
            label = "MC",
            example = "Отчищает память"
        )
        DataTypeItem(
            label = "MS",
            example = "Сохраняет последний операнд в память"
        )
        DataTypeItem(
            label = "MR",
            example = "Извлекает число из памяти"
        )
        DataTypeItem(
            label = "M+",
            example = "Складывает выражение с операндом памяти"
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = "Для каждого типа чисел своя память!",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun DataTypeItem(
    label: String,
    example: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row {
            Text(
                text = "• $label:",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Text(
            text = example,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(start = 24.dp)
        )
        Spacer(Modifier.height(8.dp))
    }
}

@Preview
@Composable
fun UniversalCalcInfoBodyPreview(){
    UniversalCalcInfoBody(
        currentPage = 0,
        onPageChanged = {}
    )
}