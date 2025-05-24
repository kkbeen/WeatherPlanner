package com.example.weatherplanner.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.weatherplanner.ui.home.HomeScreen
import com.example.weatherplanner.ui.map.MapScreen
import com.example.weatherplanner.ui.schedule.ScheduleScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.route) {
        composable(route = BottomNavItem.Home.route) {
            HomeScreen()
        }
        composable(route = BottomNavItem.Map.route) {
            MapScreen()
        }
        composable(route = BottomNavItem.Schedule.route) {
            ScheduleScreen()
        }
    }
}