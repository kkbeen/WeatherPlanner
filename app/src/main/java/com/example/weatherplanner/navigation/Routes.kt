package com.example.weatherplanner.navigation

import android.net.Uri

sealed class Routes(val route: String, val isRoot: Boolean = true) {
    object Home : Routes("home")
    object Map : Routes("map")
    object Schedule : Routes("schedule")
    object AddSchedule : Routes("add_schedule", isRoot = false)
    object PlaceRecommendation : Routes("place_recommendation")
    object EditSchedule : Routes("edit_schedule/{scheduleId}/{title}/{date}/{time}/{location}") {
        fun createRoute(scheduleId: String, title: String, date: String, time: String, location: String) =
            "edit_schedule/$scheduleId/${Uri.encode(title)}/${Uri.encode(date)}/${Uri.encode(time)}/${Uri.encode(location)}"
    }

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