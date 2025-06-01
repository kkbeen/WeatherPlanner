package com.example.weatherplanner.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherplanner.R
import com.example.weatherplanner.data.model.Schedule
import com.example.weatherplanner.data.model.repository.ScheduleRepository
import com.example.weatherplanner.ui.component.TimePickerDialog
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState()

    var pickedDateMillis by remember { mutableStateOf<Long?>(null) }

    val selectedDate = pickedDateMillis?.let {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    } ?: ""

    val selectedTime = "${timePickerState.hour.toString().padStart(2, '0')}:${
        timePickerState.minute.toString().padStart(2, '0')
    }"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("일정 등록") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("제목") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { showDatePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (selectedDate.isNotEmpty()) "선택한 날짜: $selectedDate" else "날짜 선택")
            }

            Button(
                onClick = { showTimePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("선택한 시간: $selectedTime")
            }

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("장소") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val schedule = Schedule(
                        title = title,
                        date = selectedDate,
                        time = selectedTime,
                        location = location
                    )
                    viewModel.addSchedule(schedule)
                    ScheduleRepository.saveSchedule(schedule) // Firebase에 저장
                    navController.popBackStack()
                },
                enabled = title.isNotBlank() && selectedDate.isNotBlank() && location.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("일정 추가하기")
            }
        }
    }

    // DatePickerDialog 표시
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    pickedDateMillis = datePickerState.selectedDateMillis
                        ?: Instant.now().toEpochMilli()
                    showDatePicker = false
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("취소")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // TimePickerDialog 표시
    if (showTimePicker) {
        TimePickerDialog(
            onDismiss = { showTimePicker = false },
            onConfirm = {
                // 시간 설정 완료
                showTimePicker = false
            }
        ) {
            TimeInput(state = timePickerState)
        }
    }
}
