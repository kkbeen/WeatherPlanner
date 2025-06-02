package com.example.weatherplanner.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherplanner.data.model.Condition
import com.example.weatherplanner.data.model.Hourly
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeatherRow(hourlyList: List<Hourly>?) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (hourlyList == null) {
            // 🔧 로딩용 스켈레톤 카드 6개
            items(6) {
                HourCardSkeleton()
            }
        } else {
            items(
                items = hourlyList.take(24),
                key = { it.time }
            ) { hour ->
                HourCard(hour)
            }
        }
    }
}

@Composable
fun HourCard(hour: Hourly) {
    val formattedHour = try {
        val parsed = LocalDateTime.parse(hour.time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        parsed.format(DateTimeFormatter.ofPattern("HH시"))
    } catch (e: Exception) {
        hour.time
    }

    Card(
        modifier = Modifier
            .width(80.dp)
            .height(102.dp)
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = formattedHour, style = TextStyle(fontSize = 12.sp))
            Spacer(Modifier.height(4.dp))
            Image(
                painter = rememberAsyncImagePainter("https:${hour.condition.icon}"),
                contentDescription = hour.condition.text,
                modifier = Modifier.size(40.dp)
            )
            Text(text = "${hour.temp_c}°C", style = TextStyle(fontSize = 14.sp))
        }
    }
}

@Composable
fun HourCardSkeleton() {
    Card(
        modifier = Modifier
            .width(80.dp)
            .height(102.dp)
            .padding(4.dp)
    ) {
    }
}

@Preview
@Composable
private fun HourlyWeatherRowPreview() {
    val sampleHourlyList = listOf(
        Hourly("2023-10-01 12:00", 20.0, 10, Condition("Sunny", "/path/to/icon.png")),
        Hourly("2023-10-01 13:00", 22.0, 5, Condition("Partly Cloudy", "/path/to/icon2.png"))
        // Add more sample data as needed
    )
    val sampleHourlyList2 = null
    HourlyWeatherRow(sampleHourlyList)
}