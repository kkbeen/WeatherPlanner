package com.example.weatherplanner.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.weatherplanner.viewmodel.PlaceViewModel
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PlaceScreen(viewModel: PlaceViewModel = viewModel()) {
    val context = LocalContext.current
    val places = viewModel.places.collectAsState().value

    LaunchedEffect(Unit) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    viewModel.loadNearbyPlaces(it.latitude, it.longitude)
                }
            }
        }
    }
}
