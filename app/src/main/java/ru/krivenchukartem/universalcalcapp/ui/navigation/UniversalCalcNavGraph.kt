package ru.krivenchukartem.universalcalcapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import ru.krivenchukartem.universalcalcapp.ui.screens.calculator.UniversalCalcDestination


@Composable
fun UniversalCalcNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = UniversalCalcDestination.route,
        modifier = modifier
    ){
        composable(route = UniversalCalcDestination.route){

        }
    }
}