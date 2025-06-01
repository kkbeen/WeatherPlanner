package com.example.weatherplanner.ui.home.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.weatherplanner.data.model.WeatherApiResponse

@Composable
fun WeatherStatus(weather: WeatherApiResponse?) {

    Card(
        modifier = Modifier.fillMaxWidth().height(300.dp),
    ) {
        if (weather == null) Text("날씨 정보를 불러오는 중...")
        else{

        }
    }
    if (weather == null) return Text("날씨 정보를 불러오는 중...")

    val current = weather.current
    val forecastHour = weather.forecast.forecastday[0].hour[1]
    //Text(" 위치: ${weather.location.name}")
    Text(" 시간: ${weather.location.localtime}")
    Spacer(Modifier.height(8.dp))
    Text(" 현재 기온: ${current.temp_c}°C")
    Text(" 상태: ${current.condition.text}")
    Spacer(Modifier.height(8.dp))
    Text(" 1시간 후 강수확률: ${forecastHour.chance_of_rain}%")
    Text(" 1시간 후 기온: ${forecastHour.temp_c}°C")
}