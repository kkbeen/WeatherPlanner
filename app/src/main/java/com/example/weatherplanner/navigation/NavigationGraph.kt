package com.example.weatherplanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherplanner.ui.home.HomeScreen
import com.example.weatherplanner.ui.map.MapScreen
import com.example.weatherplanner.ui.schedule.AddScheduleScreen
import com.example.weatherplanner.ui.schedule.ScheduleScreen
import com.example.weatherplanner.ui.schedule.ScheduleViewModel
import com.example.weatherplanner.viewmodel.WeatherViewModel

@Composable
fun NavigationGraph(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    NavHost(navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) { HomeScreen() }

        composable(Routes.Map.route) { MapScreen() }

        composable(Routes.Schedule.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.Schedule.route)
            }
            val viewModel = viewModel<ScheduleViewModel>(parentEntry)
            ScheduleScreen(navController, viewModel)
        }

        composable(Routes.AddSchedule.route) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(Routes.Schedule.route)
            }
            val viewModel = viewModel<ScheduleViewModel>(parentEntry)
            AddScheduleScreen(navController, viewModel)
        }
    }
}