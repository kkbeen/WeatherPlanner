package com.example.weatherplanner.ui.alarm

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherplanner.R

data class AlarmInfo(
    val id: String,
    val time: String, // 이 알람이 온 시간
    val date: String, // 해당 일정의 날짜
    val title: String,
    val message: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    onAlarmClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
    navController: NavHostController? = null
) {
    val alarms = listOf<AlarmInfo>(
        AlarmInfo("1", "08:00", "2025-10-01", "일정 알림", "Time to start your day!"),
        AlarmInfo("2", "12:00", "2025-10-02", "일정 알림", "Don't forget to eat!"),
        AlarmInfo("3", "18:00", "2025-10-03", "일정 알림", "Time to relax!"),
        AlarmInfo("4", "20:00", "2025-10-04", "일정 알림", "Prepare for tomorrow!"),
        AlarmInfo("5", "22:00", "2025-10-05", "일정 알림", "Wind down for the night!"),
        AlarmInfo("6", "07:00", "2025-10-06", "일정 알림", "Rise and shine!"),
        AlarmInfo("7", "09:00", "2025-10-07", "일정 알림", "Time for your morning routine!"),
        AlarmInfo("8", "11:00", "2025-10-08", "일정 알림", "Mid-morning check-in!"),
        AlarmInfo("9", "13:00", "2025-10-09", "일정 알림", "Lunch break!"),
        AlarmInfo("10", "15:00", "2025-10-10", "일정 알림", "Afternoon productivity boost!"),
        AlarmInfo("11", "17:00", "2025-10-11", "일정 알림", "Wrap up your work!"),
        AlarmInfo("12", "19:00", "2025-10-12", "일정 알림", "Dinner time!"),
        AlarmInfo("13", "21:00", "2025-10-13", "일정 알림", "Evening relaxation!"),
        AlarmInfo("14", "23:00", "2025-10-14", "일정 알림", "Prepare for bed!"),
        AlarmInfo("15", "05:00", "2025-10-15", "일정 알림", "Early riser alert!"),
        AlarmInfo("16", "10:00", "2025-10-16", "일정 알림", "Morning meeting reminder!"),
        AlarmInfo("17", "14:00", "2025-10-17", "일정 알림", "Afternoon coffee break!")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("알람 목록") },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 12.dp)) {
            items(alarms.size) { index ->
                val alarm = alarms[index]
                AlarmBox(
                    alarm = alarm,
                    onAlarmClick = onAlarmClick,
                    onDeleteClick = onDeleteClick
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }


}

@Preview
@Composable
private fun AlarmScreenPreview() {
    AlarmScreen(
        onAlarmClick = {},
        onDeleteClick = {}
    )
}