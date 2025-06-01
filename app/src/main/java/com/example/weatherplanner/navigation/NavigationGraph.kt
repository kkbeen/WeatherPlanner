package com.example.weatherplanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weatherplanner.ui.place.PlaceRecommendationScreen
import com.example.weatherplanner.ui.home.HomeScreen
import com.example.weatherplanner.ui.map.MapScreen
import com.example.weatherplanner.ui.schedule.AddScheduleScreen
import com.example.weatherplanner.ui.schedule.ScheduleScreen
import com.example.weatherplanner.ui.schedule.ScheduleViewModel
import com.example.weatherplanner.viewmodel.WeatherViewModel
import com.example.weatherplanner.ui.schedule.EditScheduleScreen

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

        composable(Routes.PlaceRecommendation.route) {
            PlaceRecommendationScreen(navController = navController)
        }

        composable(
            route = "${Routes.AddSchedule.route}/{placeName}/{placeAddress}",
            arguments = listOf(
                navArgument("placeName") { type = NavType.StringType },
                navArgument("placeAddress") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val placeName = backStackEntry.arguments?.getString("placeName") ?: ""
            val placeAddress = backStackEntry.arguments?.getString("placeAddress") ?: ""
            AddScheduleScreen(
                navController = navController,
                viewModel = viewModel(),
                placeName = placeName,
                placeAddress = placeAddress
            )
        }

        composable(
            "edit_schedule/{scheduleId}/{title}/{date}/{time}/{location}",
            arguments = listOf(
                navArgument("scheduleId") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("location") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("scheduleId") ?: ""  // 수정!
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val date = backStackEntry.arguments?.getString("date") ?: ""
            val time = backStackEntry.arguments?.getString("time") ?: ""
            val location = backStackEntry.arguments?.getString("location") ?: ""
            val viewModel = viewModel<ScheduleViewModel>()
            EditScheduleScreen(
                navController = navController,
                viewModel = viewModel,
                id = id,
                initTitle = title,
                initDate = date,
                initTime = time,
                initLocation = location
            )
        }
    }
}