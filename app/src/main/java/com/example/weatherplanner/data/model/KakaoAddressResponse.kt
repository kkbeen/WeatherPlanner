package com.example.weatherplanner.data.model

data class KakaoAddressResponse(
    val documents: List<AddressDocument>
)

data class AddressDocument(
    val address_name: String,
    val x: String, // 경도
    val y: String  // 위도
) 