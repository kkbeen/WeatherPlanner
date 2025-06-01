package com.example.weatherplanner.ui.place


import androidx.compose.foundation.clickable
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
import android.net.Uri
import androidx.compose.ui.unit.dp
import com.example.weatherplanner.viewmodel.PlaceViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.algorithm.LocationFetcher
import com.example.weatherplanner.data.model.algorithm.UserPreferences
import com.example.weatherplanner.navigation.Routes
import com.example.weatherplanner.viewmodel.WeatherViewModel


@Composable
fun PlaceRecommendationScreen(
    navController: NavController,
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

    val userPrefs = UserPreferences(preferredCategories = listOf("FD6", "CE7"))  // 사용자 선호 카테고리 직접 지정

    LaunchedEffect(userLat, userLon, weatherInfo) {
        if (userLat != null && userLon != null && weatherInfo != null) {
            placeViewModel.loadRecommendedPlaces(userLat!!, userLon!!, weatherInfo, userPrefs)
        }
    }


    PlaceListScreen(
        places = places,
        onPlaceClick = { place ->
            // 장소명과 주소를 경로 파라미터로 전달 (인코딩 주의)
            val encodedPlaceName = Uri.encode(place.place_name)
            val encodedAddress = Uri.encode(place.road_address_name)
            navController.navigate("${Routes.AddSchedule.route}/$encodedPlaceName/$encodedAddress")
        }
    )
}


@Composable
fun PlaceListScreen(
    places: List<Place>,
    onPlaceClick: (Place) -> Unit
) {
    LazyColumn {
        items(places) { place ->
            PlaceItem(place = place, onClick = { onPlaceClick(place) })
        }
    }
}

@Composable
fun PlaceItem(
    place: Place,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Text(place.place_name, style = MaterialTheme.typography.titleMedium)
        Text(place.road_address_name, style = MaterialTheme.typography.bodyMedium)
        Text("거리: ${place.distance}m", style = MaterialTheme.typography.bodySmall)
    }
}
