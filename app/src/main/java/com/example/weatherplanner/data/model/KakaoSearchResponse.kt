package com.example.weatherplanner.data.model

data class KakaoSearchResponse(
    val documents: List<Place>
)

data class Place(
    val place_name: String,
    val road_address_name: String,
    val distance: String,
    val x: String,
    val y: String,
    val category_group_code: String,
    val category_name: String
)

