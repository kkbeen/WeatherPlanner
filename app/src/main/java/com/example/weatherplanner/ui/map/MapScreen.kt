package com.example.weatherplanner.ui.map

import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherplanner.ui.map.component.NaverMapComponent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.repository.PlaceRepository
import com.example.weatherplanner.ui.schedule.ScheduleViewModel
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalNaverMapApi::class, ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    scheduleViewModel: ScheduleViewModel = viewModel()
) {
    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        permissionState.launchMultiplePermissionRequest()
    }

    val granted = permissionState.permissions.any { it.status.isGranted }
    val cameraPositionState = rememberCameraPositionState()
    val locationSource = rememberFusedLocationSource()
    val context = LocalContext.current

    val schedules = scheduleViewModel.scheduleList
    val places = remember { mutableStateListOf<Place>() }

    LaunchedEffect(schedules) {
        places.clear()
        for (schedule in schedules) {
            val lat: Double?
            val lon: Double?
            if (schedule.latitude != null && schedule.longitude != null) {
                lat = schedule.latitude
                lon = schedule.longitude
            } else {
                val latLng = withContext(Dispatchers.IO) {
                    PlaceRepository().geocodeAddress(schedule.location)
                }
                lat = latLng?.first
                lon = latLng?.second
            }
            if (lat != null && lon != null) {
                val place = Place(
                    place_name = schedule.title,
                    road_address_name = schedule.location,
                    distance = "",
                    x = lon.toString(),
                    y = lat.toString(),
                    category_group_code = "",
                    category_name = ""
                )
                places.add(place)
            }
        }
    }

    //강우 알림
    LaunchedEffect(granted) {
        if (granted) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnSuccessListener
                        val db = FirebaseFirestore.getInstance()
                        db.collection("users").document(uid)
                            .update(
                                mapOf(
                                    "lat" to location.latitude,
                                    "lon" to location.longitude
                                )
                            )
                            .addOnSuccessListener {
                                Log.d("Firestore", " 위치 저장 완료: (${location.latitude}, ${location.longitude})")
                            }
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(6.dp))
        NaverMapComponent(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            locationSource = if (granted) locationSource else null,
            isLocationEnabled = granted,
            places = places
        )
    }
}

@Preview
@Composable
private fun MapScreenPreview() {
    MapScreen()
}