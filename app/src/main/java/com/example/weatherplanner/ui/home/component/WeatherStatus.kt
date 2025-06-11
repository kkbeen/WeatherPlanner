package com.example.weatherplanner.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.weatherplanner.data.model.Condition
import com.example.weatherplanner.data.model.Current
import com.example.weatherplanner.data.model.Forecast
import com.example.weatherplanner.data.model.ForecastDay
import com.example.weatherplanner.data.model.Hourly
import com.example.weatherplanner.data.model.Location
import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.utils.getWeatherIconRes
import com.example.weatherplanner.utils.translateWeatherCondition

@Composable
fun WeatherStatus(weather: WeatherApiResponse?, height: Int = 110) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp),
    ) {
        if (weather != null) {
            val current = weather.current
            val iconRes = getWeatherIconRes(current.condition.text)
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = rememberAsyncImagePainter(iconRes),
                    contentDescription = current.condition.text,
                    modifier = Modifier.size(70.dp)
                )
                Row(
                    modifier = Modifier.padding(vertical = 10.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(" 현재 기온:", fontSize = 18.sp)
                        Text(" 상태:", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text("${current.temp_c}°C", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "${translateWeatherCondition(current.condition.text)}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

            }

        }
    }

}

@Preview(showBackground = true)
@Composable
private fun WeatherStatusPreview() {
    val sampleWeather = WeatherApiResponse(
        location = Location(
            name = "서울",
            localtime = "2025-06-01 13:00"
        ),
        current = Current(
            temp_c = 26.5,
            condition = Condition(text = "thundery outbreaks possible", icon = ""),
            humidity = 60
        ),
        forecast = Forecast(
            forecastday = listOf(
                ForecastDay(
                    date = "2025-06-01",
                    hour = listOf(
                        Hourly( // index 0
                            time = "2025-06-01 13:00",
                            temp_c = 26.5,
                            chance_of_rain = 10,
                            condition = Condition("Partly cloudy", "")
                        ),
                        Hourly( // index 1
                            time = "2025-06-01 14:00",
                            temp_c = 27.8,
                            chance_of_rain = 30,
                            condition = Condition("Sunny", "")
                        )
                    )
                )
            )
        )
    )

    WeatherStatus(weather = sampleWeather)
}