package com.example.weatherplanner.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherplanner.R

@Composable
fun AlarmBox(
    alarm: AlarmInfo,
    onAlarmClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAlarmClick(alarm.id) }
            .padding(vertical = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = alarm.title, style = MaterialTheme.typography.titleMedium)
                Text(text = alarm.message, style = MaterialTheme.typography.bodyMedium)
                Text(text = "${alarm.date} ${alarm.time}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = { onDeleteClick(alarm.id) }) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_close_24),
                    contentDescription = "삭제"
                )
            }
        }
    }
}

