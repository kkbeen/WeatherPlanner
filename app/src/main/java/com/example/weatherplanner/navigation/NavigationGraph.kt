package com.example.weatherplanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.weatherplanner.ui.alarm.AlarmScreen
import com.example.weatherplanner.ui.home.HomeScreen
import com.example.weatherplanner.ui.login.AuthCheckScreen
import com.example.weatherplanner.ui.login.LoginScreen
import com.example.weatherplanner.ui.login.SignUpScreen
import com.example.weatherplanner.ui.map.MapScreen
import com.example.weatherplanner.ui.place.PlaceRecommendationScreen
import com.example.weatherplanner.ui.schedule.AddScheduleScreen
import com.example.weatherplanner.ui.schedule.EditScheduleScreen
import com.example.weatherplanner.ui.schedule.ScheduleScreen
import com.example.weatherplanner.ui.schedule.ScheduleViewModel
import com.example.weatherplanner.viewmodel.AuthViewModel
import com.example.weatherplanner.viewmodel.WeatherViewModel


@Composable
fun NavigationGraph(navController: NavHostController, weatherViewModel: WeatherViewModel) {
    NavHost(navController, startDestination = "auth_check") {

        composable(Routes.AuthCheck.route) {
            AuthCheckScreen(navController)
        }

        composable(Routes.Login.route) {
            val context = LocalContext.current
            val viewModel = viewModel<AuthViewModel>()

            LoginScreen(
                navController = navController,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                viewModel = viewModel
            )
        }

        composable(Routes.SignUp.route) {
            SignUpScreen(navController)
        }

        composable(Routes.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Routes.Map.route) {
            MapScreen()
        }

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
            Routes.EditSchedule.route,
            arguments = listOf(
                navArgument("scheduleId") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("date") { type = NavType.StringType },
                navArgument("time") { type = NavType.StringType },
                navArgument("location") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("scheduleId") ?: ""
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

        composable(Routes.Alarm.route) {
            AlarmScreen(navController = navController)
        }




    }
}
