package com.example.weatherplanner.ui.place


import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.weatherplanner.R
import com.example.weatherplanner.data.model.Place
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.data.model.algorithm.UserPreferences
import com.example.weatherplanner.navigation.Routes
import com.example.weatherplanner.viewmodel.PlaceViewModel
import com.example.weatherplanner.viewmodel.WeatherViewModel

@Composable
fun PlaceRecommendationScreen(
    navController: NavController,
    placeViewModel: PlaceViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel()
) {
    val places by placeViewModel.places.collectAsState()
    val weatherInfo by weatherViewModel.weather.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) { // key를 Unit으로 하여 최초 1회만 실행
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            val locationClient = LocationServices.getFusedLocationProviderClient(context)
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    weatherViewModel.fetchWeather(it.latitude, it.longitude)
                }
            }
        }
    }

    val userPrefs =
        UserPreferences(preferredCategories = listOf("FD6", "CE7", "CT1"))  // 사용자 선호 카테고리 (문화시설 추가)

    LaunchedEffect(weatherInfo) {
        weatherInfo?.let {
            val lat = it.location.lat
            val lon = it.location.lon
            placeViewModel.loadRecommendedPlaces(lat, lon, it, userPrefs)
        }
    }


    PlaceListScreen(
        places = places,
        weatherInfo = weatherInfo,
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
    weatherInfo: WeatherApiResponse?,
    onPlaceClick: (Place) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(places) { index, place ->
            PlaceItem(place = place, weatherInfo = weatherInfo, onClick = { onPlaceClick(place) })
            if (index < places.size - 1) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp), // 좌우 여백 줄 수 있음
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun PlaceItem(
    place: Place,
    weatherInfo: WeatherApiResponse?,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconPath = getCategoryIconRes(place.category_group_code)
        Image(
            painter = painterResource(id = iconPath),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier.padding(end = 8.dp).fillMaxWidth()
        ) {
            Text(place.place_name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(getRecommendationMessage(place, weatherInfo), style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${place.distance}m", style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 2.dp))
                Spacer(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .width(1.dp)
                        .height(14.dp)
                        .background(Color.Gray)
                )
                Text(place.road_address_name, style = MaterialTheme.typography.bodySmall)
            }
        }
    }

}

fun getCategoryIconRes(category: String): Int {
    return when (category) {
        "PM9" -> R.drawable.pharmacy
        "PK6" -> R.drawable.parking_lot // 주차장
        "BK9" -> R.drawable.bank // 은행
        "CS2" -> R.drawable.convenience_store // 편의점
        "FD6" -> R.drawable.food // 음식점
        "CE7" -> R.drawable.caffee // 카페
        "CT1" -> R.drawable.culture // 문화/영화
        "OL7" -> R.drawable.gas_station // 주유소
        "AD5" -> R.drawable.hotel // 호텔
        else -> R.drawable.location // 기본값 (없을 때)
    }
}

@Preview(showBackground = true)
@Composable
fun PlaceItemPreview() {
    val samplePlace = Place(
        place_name = "테스트 장소",
        road_address_name = "서울시 강남구 테헤란로 123",
        distance = 500.toString(),
        category_group_code = "FD6",
        x = 1000.toString(),
        y = 1000.toString(),
        category_name = "음식점",
    )

    // WeatherApiResponse는 null 또는 테스트용 값 전달
    PlaceItem(
        place = samplePlace,
        weatherInfo = null,
        onClick = {}
    )
}