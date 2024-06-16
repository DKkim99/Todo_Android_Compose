package com.dkproject.todoapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dkproject.todoapp.presentation.ui.screen.Home.HomeScreen


enum class TodoNavigationGraph(val description: String, val icon: ImageVector) {
    HOME(description = "Todo", Icons.Default.TaskAlt),
    CALENDAR(description = "Calendar", Icons.Default.CalendarMonth),
}


@Composable
fun TodoNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    Scaffold(bottomBar = {
        TodoNavigationBottomBar(navController = navHostController)
    }) { innerPadding ->
        NavHost(
            modifier = Modifier,
            navController = navHostController, startDestination = TodoNavigationGraph.HOME.name
        ) {
            composable(route = TodoNavigationGraph.HOME.name) {
                HomeScreen(innerPadding = innerPadding)
            }

            composable(route = TodoNavigationGraph.CALENDAR.name) {

            }
        }
    }
}

@Composable
fun TodoNavigationBottomBar(
    navController: NavController
) {
    val navigationItem = listOf(TodoNavigationGraph.HOME, TodoNavigationGraph.CALENDAR)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentItem: TodoNavigationGraph = TodoNavigationGraph.valueOf(
        backStackEntry?.destination?.route ?: TodoNavigationGraph.HOME.name
    )
    NavigationBar(modifier = Modifier) {
        navigationItem.forEach { item ->
            NavigationBarItem(selected = currentItem == item, onClick = { /*TODO*/ }, icon = {
                Icon(imageVector = item.icon, contentDescription = item.description)
            },
                label = {
                    Text(text = item.description)
                })
        }
    }
}