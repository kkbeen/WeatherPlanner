package com.example.weatherplanner.navigation

import android.net.Uri

sealed class Routes(val route: String, val isRoot: Boolean = true) {
    object Home : Routes("home")
    object Map : Routes("map")
    object Schedule : Routes("schedule")
    object AddSchedule : Routes("add_schedule", isRoot = false)
    object PlaceRecommendation : Routes("place_recommendation")
    object EditSchedule : Routes("edit_schedule/{scheduleId}/{title}/{date}/{time}/{location}") {
        fun createRoute(
            scheduleId: String,
            title: String,
            date: String,
            time: String,
            location: String
        ) =
            "edit_schedule/$scheduleId/${Uri.encode(title)}/${Uri.encode(date)}/${Uri.encode(time)}/${
                Uri.encode(
                    location
                )
            }"
    }

    // 로그인 관련 경로 추가
    object Login : Routes("login", isRoot = false)
    object SignUp : Routes("signup", isRoot = false)
    object AuthCheck : Routes("auth_check", isRoot = false)

    // 알람 화면 경로 추가
    object Alarm : Routes("alarm", isRoot = false)

    companion object {
        fun getRoute(route: String?): Routes {
            return when (route) {
                Map.route -> Map
                Schedule.route -> Schedule
                AddSchedule.route -> AddSchedule
                Login.route -> Login
                SignUp.route -> SignUp
                AuthCheck.route -> AuthCheck
                Alarm.route -> Alarm
                else -> Home
            }
        }
    }
}