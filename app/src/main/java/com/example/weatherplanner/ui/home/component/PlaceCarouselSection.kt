package com.example.weatherplanner.ui.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.WeatherApiResponse
import com.example.weatherplanner.ui.place.getRecommendationMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlaceCarouselSection(
    places: List<Place>,
    weatherInfo: WeatherApiResponse?,
    onPlaceClick: (Place) -> Unit
) {
    if (places.isEmpty()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "추천 장소가 없습니다.",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
        return
    }

    val pageCount = places.size
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { pageCount })
    val coroutineScope = rememberCoroutineScope()

    // 자동 슬라이드
    LaunchedEffect(pagerState.currentPage) {
        delay(5000)
        coroutineScope.launch {
            val nextPage = (pagerState.currentPage + 1) % pageCount
            pagerState.animateScrollToPage(nextPage)
        }
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) { page ->
        val place = places[page % places.size]

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clickable { onPlaceClick(place) },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = place.place_name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = place.road_address_name, fontSize = 12.sp, color = Color.Gray)
                Text(text = "거리: ${place.distance}m", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = getRecommendationMessage(place, weatherInfo),
                    fontSize = 14.sp,
                    color = Color(0xFF2A8AF6)
                )
            }
        }
    }
}