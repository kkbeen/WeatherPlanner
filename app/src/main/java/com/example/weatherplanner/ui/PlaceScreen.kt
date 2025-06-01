package com.example.weatherplanner.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.weatherplanner.viewmodel.PlaceViewModel
import com.google.android.gms.location.LocationServices
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.algorithm.LocationFetcher
import com.example.weatherplanner.viewmodel.WeatherViewModel

@Composable
fun PlaceRecommendationScreen(
    placeViewModel: PlaceViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val places by placeViewModel.places.collectAsState()
    val weatherInfo by weatherViewModel.weather.collectAsState()

    var userLat by remember { mutableStateOf<Double?>(null) }
    var userLon by remember { mutableStateOf<Double?>(null) }

    LocationFetcher { lat, lon ->
        userLat = lat
        userLon = lon
        weatherViewModel.fetchWeather(lat, lon)
    }

    LaunchedEffect(userLat, userLon, weatherInfo) {
        if (userLat != null && userLon != null && weatherInfo != null) {
            placeViewModel.loadRecommendedPlaces(userLat!!, userLon!!, weatherInfo)
        }
    }

    PlaceListScreen(places = places)
}

@Composable
fun PlaceListScreen(places: List<Place>) {
    LazyColumn {
        items(places) { place ->
            PlaceItem(place)
        }
    }
}

@Composable
fun PlaceItem(place: Place) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(place.place_name, style = MaterialTheme.typography.titleMedium)
        Text(place.road_address_name, style = MaterialTheme.typography.bodyMedium)
        Text("거리: ${place.distance}m", style = MaterialTheme.typography.bodySmall)
    }
}
