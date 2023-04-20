package com.example.composetest

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationView(navController: NavHostController,
                   padding: PaddingValues
) {


    NavHost(
        navController = navController,
        startDestination = "SleepTimeChart",
        modifier = Modifier.padding(paddingValues = padding),
        builder = {



            composable("SleepTimeChart") {
               /* SleepTimeChart(values = hours)*/
                StackedBarChart(data = stackedData, colors = stackedColors)
            }
            composable("SleepDetails") {
                DetailsView()
            }
        })

}