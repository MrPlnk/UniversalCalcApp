package ru.krivenchukartem.universalcalcapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import ru.krivenchukartem.universalcalcapp.ui.navigation.UniversalCalcNavGraph


@Composable
fun UniversalCalcApp() {
    UniversalCalcNavGraph(navController = rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversalCalcTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateBack: () -> Unit = {}
){
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = { Text(text = title) },
        navigationIcon = {
            if (canNavigateBack){
                IconButton(
                    onClick = navigateBack
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_back)
                    )
                }
            }
        }

    )
}