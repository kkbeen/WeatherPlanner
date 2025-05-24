package com.example.weatherplanner.ui.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherplanner.navigation.Routes

@Composable
fun ScheduleScreen(
    navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Routes.AddSchedule.route)
            }) {
                Icon(Icons.Default.Add, contentDescription = "일정 추가")
            }
        }
    ) { innerPaddng ->
        Box(
            modifier = Modifier
                .padding(innerPaddng)
                .fillMaxSize()
        ) {
            Text("일정 화면입니다", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    val navHostController = rememberNavController()
    ScheduleScreen(navHostController)
}