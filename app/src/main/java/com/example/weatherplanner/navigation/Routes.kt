package com.example.weatherplanner.navigation

sealed class Routes(val route: String, val isRoot: Boolean = true) {
    object Home : Routes("home")
    object Map : Routes("map")
    object Schedule : Routes("schedule")
    object AddSchedule : Routes("add_schedule", isRoot = false)

    companion object {
        fun getRoute(route: String?): Routes {
            return when (route) {
                Map.route -> Map
                Schedule.route -> Schedule
                AddSchedule.route -> AddSchedule
                else -> Home
            }
        }
    }
}