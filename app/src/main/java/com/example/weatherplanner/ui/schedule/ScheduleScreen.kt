package com.example.weatherplanner.ui.schedule

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.weatherplanner.navigation.Routes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    val schedules = viewModel.scheduleList

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // 날짜별로 그룹핑 후 시간순 정렬
    val groupedSchedules = schedules.groupBy { it.date }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("일정 관리") },
                actions = {
                    IconButton(onClick = {
                        viewModel.loadSchedulesFromFirebase()
                        scope.launch {
                            snackbarHostState.showSnackbar("일정을 새로고침했습니다.")
                        }
                    }) {
                        Icon(Icons.Default.Refresh, contentDescription = "새로고침")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Routes.AddSchedule.route) }
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
                groupedSchedules.forEach { (date, scheduleList) ->

                    stickyHeader {
                        Card(
                            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                        ){
                            Text(
                                text = "$date",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            )
                        }

                    }

                    items(scheduleList) { schedule ->
                        ScheduleCard(
                            schedule = schedule,
                            onEditClick = {
                                navController.navigate(
                                    Routes.EditSchedule.createRoute(
                                        schedule.id,
                                        schedule.title,
                                        schedule.date,
                                        schedule.time,
                                        schedule.location
                                    )
                                )
                            },
                            onDeleteClick = {
                                viewModel.removeSchedule(schedule.id)
                                scope.launch {
                                    snackbarHostState.showSnackbar("일정을 삭제했습니다.")
                                }
                            }
                        )
                    }
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