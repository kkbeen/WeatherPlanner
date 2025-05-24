package com.example.weatherplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherplanner.ui.home.HomeScreen
import com.example.weatherplanner.ui.map.MapScreen
import com.example.weatherplanner.ui.schedule.AddScheduleScreen
import com.example.weatherplanner.ui.schedule.ScheduleScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Routes.Home.route) {
        composable(Routes.Home.route) { HomeScreen() }
        composable(Routes.Map.route) { MapScreen() }
        composable(Routes.Schedule.route) { ScheduleScreen(navController) }
        composable(Routes.AddSchedule.route) { AddScheduleScreen(navController) }
    }
}