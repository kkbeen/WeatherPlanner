package com.example.weatherplanner.ui.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
//    if (places.isEmpty()) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(180.dp)
//                .padding(8.dp),
//            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//        ) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "추천 장소가 없습니다.",
//                    fontSize = 14.sp,
//                    color = Color.Gray
//                )
//            }
//        }
//        return
//    }

    // 테스트용 예시 장소 데이터 listOf("FD6", "CE7", "CT1", "AT4") // 음식점, 카페, 문화시설, 관광지
    val dummyPlaces = listOf(
        Place(
            place_name = "카페 온더클라우드",
            road_address_name = "서울특별시 강남구 테헤란로 123",
            distance = "320",
            x = "127.027583",
            y = "37.497942",
            category_group_code = "CE7",
            category_name = "카페 > 커피전문점"
        ),
        Place(
            place_name = "한강 시민공원",
            road_address_name = "서울특별시 영등포구 여의도동 123",
            distance = "1500",
            x = "126.924807",
            y = "37.528788",
            category_group_code = "AT4",
            category_name = "관광명소 > 공원"
        ),
        Place(
            place_name = "서울숲",
            road_address_name = "서울특별시 성동구 성수동1가 685",
            distance = "2700",
            x = "127.037222",
            y = "37.544579",
            category_group_code = "AT4",
            category_name = "관광명소 > 도시공원"
        ),
        Place(
            place_name = "DDP 디자인 플라자",
            road_address_name = "서울특별시 중구 을지로7가 2-1",
            distance = "4200",
            x = "127.008889",
            y = "37.566295",
            category_group_code = "CT1",
            category_name = "문화시설 > 전시관"
        ),
        Place(
            place_name = "백종원의 더본포차",
            road_address_name = "서울특별시 마포구 서교동 358-61",
            distance = "1200",
            x = "126.922426",
            y = "37.556655",
            category_group_code = "FD6",
            category_name = "음식점 > 한식"
        )
    )
    val displayPlaces = if (places.isEmpty()) dummyPlaces else places
    // ======= 테스트용 예시 장소 데이터 끝 =======

    //val pageCount = places.size ⬇️
    val pageCount = displayPlaces.size
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
        // val place = places[page % places.size]⬇️
        val place = displayPlaces[page % displayPlaces.size]

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clickable { onPlaceClick(place) },
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = place.place_name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${place.distance}m",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .width(1.dp)
                            .height(14.dp)
                            .background(Color.Gray)
                    )
                    Text(text = place.road_address_name, fontSize = 12.sp, color = Color.Gray)
                }
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