package com.example.weatherplanner.data.model

data class KakaoSearchResponse(
    val documents: List<Place>
)

data class Place(
    val place_name: String,
    val road_address_name: String,
    val distance: String,
    val x: String,  // 경도
    val y: String   // 위도
)

