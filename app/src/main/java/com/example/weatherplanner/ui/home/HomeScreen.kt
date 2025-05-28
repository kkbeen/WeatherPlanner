package com.example.weatherplanner.ui.home
import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.weatherplanner.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(viewModel: WeatherViewModel = viewModel()) {
    val context = LocalContext.current
    val weather = viewModel.weather.collectAsState().value

    LaunchedEffect(Unit) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    viewModel.fetchWeather(it.latitude, it.longitude)
                }
            }
        }
    }

    weather?.let { data ->
        val current = data.current
        val forecastHour = data.forecast.forecastday[0].hour[1] // 1시간 후 예보

        Column(Modifier.padding(16.dp)) {
            Text(" 위치: ${data.location.name}")
            Text(" 시간: ${data.location.localtime}")
            Spacer(Modifier.height(8.dp))
            Text(" 현재 기온: ${current.temp_c}°C")
            Text(" 상태: ${current.condition.text}")
            Spacer(Modifier.height(8.dp))
            Text(" 1시간 후 강수확률: ${forecastHour.chance_of_rain}%")
            Text(" 1시간 후 기온: ${forecastHour.temp_c}°C")
        }
    } ?: Text("날씨 정보를 불러오는 중...")
}
