package com.example.weatherplanner.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.ui.place.getRecommendationMessage

@Composable
fun PlaceRecommendationSection(
    places: List<Place>,
    weatherInfo: WeatherApiResponse?,
    onPlaceClick: (Place) -> Unit
) {
    Column {
        if (places.isEmpty()) {
            Text("추천 장소가 없습니다.", fontSize = 14.sp, color = Color.Gray)
        } else {
            places.take(5).forEach { place ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPlaceClick(place) }
                        .padding(vertical = 6.dp)
                ) {
                    Text(text = place.place_name, fontWeight = FontWeight.SemiBold)
                    Text(text = place.road_address_name, fontSize = 12.sp, color = Color.Gray)
                    Text(text = "거리: ${place.distance}m", fontSize = 12.sp, color = Color.Gray)
                    Text(
                        text = getRecommendationMessage(place, weatherInfo),
                        fontSize = 12.sp,
                        color = Color(0xFF2A8AF6) // 원하는 색상으로!
                    )
                }
            }
        }
    }
}