package com.example.composetest

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val bottomNavItems = listOf(
        BottomNavItem(
            label = "Sleep Quality",
            route = "SleepQualityChart",
        ),
        BottomNavItem(
            label = "Sleep Hours",
            route = "SleepTimeChart",
        )

    )

    BottomNavigation(
        backgroundColor = colors.primary
    ) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        // observe current route to change the icon
        // color,label color when navigated
        val currentRoute = navBackStackEntry?.destination?.route

        // Bottom nav items we declared
        bottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            BottomNavigationItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {},

                // label
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = true
            )
        }
    }
}
