package com.example.weatherplanner.navigation

import com.example.weatherplanner.R

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            route = "home",
            label = "홈",
            icon = R.drawable.outline_home_24,
            selectedIcon = R.drawable.baseline_home_24
        ),
        BarItem(
            route = "map",
            label = "지도",
            icon = R.drawable.outline_map_24,
            selectedIcon = R.drawable.baseline_map_24
        ),
        BarItem(
            route = "schedule",
            label = "일정",
            icon = R.drawable.outline_calendar_today_24,
            selectedIcon = R.drawable.baseline_calendar_today_24
        ),
        BarItem(
            route = "place_recommendation",
            label = "장소추천",
            icon = R.drawable.outline_add_location_24,       // 적절한 아이콘 리소스로 교체
            selectedIcon = R.drawable.baseline_add_location_24  // 선택된 상태 아이콘
        )
    )
}