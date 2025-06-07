package com.example.weatherplanner.ui.map

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherplanner.ui.home.component.WeatherStatus
import com.example.weatherplanner.ui.map.component.NaverMapComponent
import com.example.weatherplanner.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(viewModel: WeatherViewModel = viewModel()) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    // 권한 요청
    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    val granted = permissionState.permissions.any { it.status.isGranted }

    val cameraPositionState = rememberCameraPositionState()
    val locationSource = rememberFusedLocationSource()

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

    // 지도 출력
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherStatus(weather)
        Spacer(modifier = Modifier.height(6.dp))
        NaverMapComponent(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            locationSource = if (granted) locationSource else null,
            isLocationEnabled = granted
        )
    }

}

@Preview
@Composable
private fun MapScreenPreview() {
    MapScreen()
}