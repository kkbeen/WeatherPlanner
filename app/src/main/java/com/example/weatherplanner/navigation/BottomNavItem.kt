package com.example.weatherplanner.navigation

import com.example.weatherplanner.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: Int
) {
    object Home : BottomNavItem("home", "홈", R.drawable.outline_home_24)
    object Map : BottomNavItem("map", "지도", R.drawable.outline_map_search_24)
    object Schedule : BottomNavItem("schedule", "일정", R.drawable.outline_calendar_today_24)

    companion object {
        val bottomItems = listOf(Home, Map, Schedule)
    }
}