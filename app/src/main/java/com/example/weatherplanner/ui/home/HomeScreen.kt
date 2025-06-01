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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherplanner.R
import com.example.weatherplanner.ui.home.component.WeatherStatus
import com.example.weatherplanner.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: WeatherViewModel = viewModel()) {
    val context = LocalContext.current
    val weather = viewModel.weather.collectAsState().value

    // 위치 권한 및 날씨 가져오기
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
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color.Unspecified,
            title = {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(R.drawable.weather_planner_logo),
                    contentDescription = "weatherplanner logo",
                    tint = Color.Unspecified
                )
            },
            actions = {
                Row(
                    modifier = Modifier.padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (weather != null) {
                        Text(text = "${weather.location.name}")
                    }

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
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}