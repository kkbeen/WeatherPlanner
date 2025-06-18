package com.example.weatherplanner.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherplanner.R

@Composable
fun AlarmBox(
    alarm: AlarmInfo,
    onAlarmClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onAlarmClick(alarm.id) }
            .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Text(
            text = alarm.time,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.outline_close_24),
            contentDescription = "Delete Icon",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
                .clickable { onDeleteClick(alarm.id) },
            tint = Color.DarkGray
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.outline_calendar_today_24),
                contentDescription = "Alarm Icon",
                modifier = Modifier.size(48.dp),
                tint = Color.DarkGray
            )
            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = alarm.date + " " + alarm.title,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = alarm.message,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlarmBox() {
    val alarm = AlarmInfo("1", "08:00", "2025-10-01", "일정 알림", "Time to start your day!")
    AlarmBox(
        alarm = alarm,
        onAlarmClick = {},
        onDeleteClick = {}
    )
}