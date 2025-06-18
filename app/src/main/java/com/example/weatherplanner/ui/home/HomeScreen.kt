package com.example.weatherplanner.ui.home

import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import com.example.weatherplanner.R
import com.example.weatherplanner.data.model.algorithm.UserPreferences
import com.example.weatherplanner.navigation.Routes
import com.example.weatherplanner.ui.home.component.CategorySelector
import com.example.weatherplanner.ui.home.component.HourlyWeatherRow
import com.example.weatherplanner.ui.home.component.PlaceCarouselSection
import com.example.weatherplanner.ui.home.component.WeatherStatus
import com.example.weatherplanner.ui.home.component.categoryMap
import com.example.weatherplanner.viewmodel.PlaceViewModel
import com.example.weatherplanner.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    weatherViewModel: WeatherViewModel = viewModel(),
    placeViewModel: PlaceViewModel = viewModel(),
    navController: NavController? = null
) {
    val context = LocalContext.current
    val weather = weatherViewModel.weather.collectAsState().value
    val places = placeViewModel.places.collectAsState().value

    var userLat by remember { mutableStateOf<Double?>(null) }
    var userLon by remember { mutableStateOf<Double?>(null) }

    var selectedCategoryCode by remember { mutableStateOf("ALL") }
    val availableCategoryCodes = places.map { it.category_group_code }.toSet()
    val filteredCategoryMap = categoryMap.filter { (code, _) ->
        code == "ALL" || code in availableCategoryCodes
    }

    val weatherInfo by weatherViewModel.weather.collectAsState()

    LaunchedEffect(Unit) {
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    userLat = it.latitude
                    userLon = it.longitude
                    weatherViewModel.fetchWeather(it.latitude, it.longitude)
                }
            }
        }
    }

    val userPrefs = remember {
        UserPreferences(preferredCategories = listOf("FD6", "CE7"))
    }

    LaunchedEffect(weather) {
        if (userLat != null && userLon != null && weather != null) {
            placeViewModel.loadRecommendedPlaces(userLat!!, userLon!!, weather, userPrefs)
        }
    }

    val filteredPlaces = remember(places, selectedCategoryCode) {
        if (selectedCategoryCode == "ALL") places
        else places.filter { it.category_group_code == selectedCategoryCode }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp),
                backgroundColor = Color.White,
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
                            Text(
                                text = "${weather.location.name}",
                                style = TextStyle(fontSize = 14.sp, color = Color.Black)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    navController?.navigate(Routes.Alarm.route)
                                },
                            painter = painterResource(id = R.drawable.ic_home_bell),
                            contentDescription = "home bell icon",
                            tint = Color.Unspecified
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            WeatherStatus(weather)
            Spacer(Modifier.height(16.dp))
            HourlyWeatherRow(
                hourlyList = weather?.forecast?.forecastday?.firstOrNull()?.hour
            )
            Spacer(Modifier.height(20.dp))

            CategorySelector(
                selectedCategory = selectedCategoryCode,
                onCategorySelected = { selectedCategoryCode = it },
                categoryOptions = filteredCategoryMap
            )
            PlaceCarouselSection(
                places = filteredPlaces,
                weatherInfo = weatherInfo,
                onPlaceClick = { place ->
                    navController?.let {
                        val name = Uri.encode(place.place_name)
                        val address = Uri.encode(place.road_address_name)
                        it.navigate("${Routes.AddSchedule.route}/$name/$address")
                    }
                }
            )

        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}