package com.example.weatherplanner.ui.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherplanner.navigation.Routes

@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    val schedules = viewModel.scheduleList

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.AddSchedule.route)
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "일정 추가")
            }
        }
    ) { innerPadding ->
        if (schedules.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("등록된 일정이 없습니다.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                items(schedules.size) { index ->
                    ScheduleCard(schedule = schedules[index])
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScheduleScreenPreview() {
    val navHostController = rememberNavController()
    ScheduleScreen(navHostController)
}