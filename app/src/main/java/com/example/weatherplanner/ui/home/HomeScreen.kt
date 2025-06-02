package com.example.weatherplanner.ui.home

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherplanner.R
import com.example.weatherplanner.ui.home.component.HourlyWeatherRow
import com.example.weatherplanner.ui.home.component.WeatherStatus
import com.example.weatherplanner.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: WeatherViewModel = viewModel()) {
    val context = LocalContext.current
    val weather = viewModel.weather.collectAsState().value

    LaunchedEffect(Unit) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    viewModel.fetchWeather(it.latitude, it.longitude)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            backgroundColor = Color.Unspecified,
            elevation = 1.dp,
            title = {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(R.drawable.weather_planner_logo),
                    contentDescription = "weatherplanner logo",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "오늘 뭐해?",
                    style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 16.sp),
                    color = Color.Black
                )
            },
            actions = {
                Row(
                    modifier = Modifier.padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_home_location),
                        contentDescription = "location icon",
                        tint = Color.Unspecified
                    )
                    if (weather != null) {
                        Text(text = "${weather.location.name}", style = TextStyle(fontSize = 14.sp, color = Color.Black))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_home_bell),
                        contentDescription = "home bell icon",
                        tint = Color.Unspecified
                    )
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        WeatherStatus(weather)

        Spacer(Modifier.height(16.dp))

        HourlyWeatherRow(
            hourlyList = weather?.forecast?.forecastday?.firstOrNull()?.hour
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}