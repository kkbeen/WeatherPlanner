package com.example.weatherplanner.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.navigation.NavController
import com.example.weatherplanner.data.model.Schedule
import com.example.weatherplanner.ui.component.TimePickerDialog
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScheduleScreen(
    navController: NavController,
    viewModel: ScheduleViewModel,
    id: String,
    initTitle: String,
    initDate: String,
    initTime: String,
    initLocation: String
) {
    var title by remember { mutableStateOf(initTitle) }
    var location by remember { mutableStateOf(initLocation) }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var date by remember { mutableStateOf(initDate) }
    var time by remember { mutableStateOf(initTime) }

    val startTime = "${date}T${time}" // 추가

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            Instant.from(formatter.parse(initDate)).toEpochMilli()
        } catch (e: Exception) { null }
    )
    val timePickerState = rememberTimePickerState().apply {
        val (h, m) = initTime.split(":").mapNotNull { it.toIntOrNull() }
        if (h != null && m != null) {
            this.hour = h
            this.minute = m
        }
    }

    fun formatDate(millis: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
    }

    fun formatTime(hour: Int, minute: Int): String {
        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
    }

    Scaffold(
        topBar = {
            androidx.compose.material3.TopAppBar(
                title = { Text("일정 수정") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = com.example.weatherplanner.R.drawable.outline_arrow_back_ios_new_24),
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                Text(if (date.isNotEmpty()) "선택한 날짜: $date" else "날짜 선택")
            }

            Button(
                onClick = { showTimePicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("선택한 시간: $time")
            }

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("장소") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        val updated = Schedule(
                            id = id,
                            title = title,
                            date = date,
                            time = time,
                            location = location,
                            startTime = startTime     //추가
                        )
                        viewModel.updateSchedule(updated)
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f),
                    enabled = title.isNotBlank() && date.isNotBlank() && time.isNotBlank() && location.isNotBlank()
                ) {
                    Text("수정 저장")
                }
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        viewModel.removeSchedule(id)
                        navController.popBackStack()
                    }
                ) {
                    Text("삭제")
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        date = formatDate(it)
                    }
                    showDatePicker = false
                }) { Text("확인") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("취소") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        TimePickerDialog(
            onDismiss = { showTimePicker = false },
            onConfirm = {
                time = formatTime(timePickerState.hour, timePickerState.minute)
                showTimePicker = false
            }
        ) {
            TimeInput(state = timePickerState)
        }
    }
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EditScheduleScreen(
//    navController: NavController,
//    viewModel: ScheduleViewModel,
//    id: String,
//    initTitle: String,
//    initDate: String,
//    initTime: String,
//    initLocation: String
//) {
//    var title by remember { mutableStateOf(initTitle) }
//    var location by remember { mutableStateOf(initLocation) }
//
//    // 날짜/시간 Picker 관리 변수
//    var showDatePicker by remember { mutableStateOf(false) }
//    var showTimePicker by remember { mutableStateOf(false) }
//
//    // 날짜/시간 state
//    var date by remember { mutableStateOf(initDate) }
//    var time by remember { mutableStateOf(initTime) }
//
//    val datePickerState = rememberDatePickerState(
//        // initDate가 yyyy-MM-dd 형식이면 초기값 세팅, 아니면 null
//        initialSelectedDateMillis = try {
//            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//            Instant.from(formatter.parse(initDate)).toEpochMilli()
//        } catch (e: Exception) { null }
//    )
//    val timePickerState = rememberTimePickerState().apply {
//        // initTime이 HH:mm 형식이면 초기값 세팅
//        val (h, m) = initTime.split(":").mapNotNull { it.toIntOrNull() }
//        if (h != null && m != null) {
//            this.hour = h
//            this.minute = m
//        }
//    }
//
//    // 날짜 포맷 함수
//    fun formatDate(millis: Long): String {
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate().format(formatter)
//    }
//
//    // 시간 포맷 함수
//    fun formatTime(hour: Int, minute: Int): String {
//        return "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        // 제목
//        OutlinedTextField(
//            value = title,
//            onValueChange = { title = it },
//            label = { Text("제목") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        // 날짜
//        Button(
//            onClick = { showDatePicker = true },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(if (date.isNotEmpty()) "선택한 날짜: $date" else "날짜 선택")
//        }
//
//        // 시간
//        Button(
//            onClick = { showTimePicker = true },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("선택한 시간: $time")
//        }
//
//        // 장소
//        OutlinedTextField(
//            value = location,
//            onValueChange = { location = it },
//            label = { Text("장소") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Button(
//                onClick = {
//                    val updated = Schedule(
//                        id = id,
//                        title = title,
//                        date = date,
//                        time = time,
//                        location = location
//                    )
//                    viewModel.updateSchedule(updated)
//                    navController.popBackStack()
//                },
//                modifier = Modifier.weight(1f),
//                enabled = title.isNotBlank() && date.isNotBlank() && time.isNotBlank() && location.isNotBlank()
//            ) {
//                Text("수정 저장")
//            }
//            Button(
//                modifier = Modifier.weight(1f),
//                onClick = {
//                    viewModel.removeSchedule(id)
//                    navController.popBackStack()
//                }
//            ) {
//                Text("삭제")
//            }
//        }
//    }
//
//    // DatePickerDialog
//    if (showDatePicker) {
//        DatePickerDialog(
//            onDismissRequest = { showDatePicker = false },
//            confirmButton = {
//                TextButton(onClick = {
//                    datePickerState.selectedDateMillis?.let {
//                        date = formatDate(it)
//                    }
//                    showDatePicker = false
//                }) { Text("확인") }
//            },
//            dismissButton = {
//                TextButton(onClick = { showDatePicker = false }) { Text("취소") }
//            }
//        ) {
//            DatePicker(state = datePickerState)
//        }
//    }
//
//    // TimePickerDialog
//    if (showTimePicker) {
//        TimePickerDialog(
//            onDismiss = { showTimePicker = false },
//            onConfirm = {
//                time = formatTime(timePickerState.hour, timePickerState.minute)
//                showTimePicker = false
//            }
//        ) {
//            TimeInput(state = timePickerState)
//        }
//    }
//}