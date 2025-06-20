package com.example.weatherplanner.data.model.repository

import com.example.weatherplanner.data.model.KakaoSearchResponse
import com.example.weatherplanner.data.model.Place
import com.example.weatherplanner.data.model.network.RetrofitKakaoInstance
import retrofit2.HttpException

class PlaceRepository {
    private val apiKeys = listOf(
        "KakaoAK 0fe79841a708fb2e34df30d7a5ca51dc", // 메인
        "KakaoAK 2b1ee159643f483302cc365fa09589c4"  // 서브
    )

    suspend fun searchMultipleCategories(
        lat: Double,
        lon: Double
    ): List<Place> {
        val categories = listOf("FD6", "CE7", "CT1", "AT4") // 음식점, 카페, 문화시설, 관광지
        val allResults = mutableListOf<Place>()

        for (category in categories) {
            val result = safeKakaoApiCall(category, lon, lat)
            if (result != null) {
                allResults.addAll(result.documents)
            }
        }

        return allResults
    }

    private suspend fun safeKakaoApiCall(
        category: String,
        lon: Double,
        lat: Double
    ): KakaoSearchResponse? {
        for (apiKey in apiKeys) {
            try {
                return RetrofitKakaoInstance.api.searchPlacesByCategory(
                    apiKey = apiKey,
                    categoryCode = category,
                    longitude = lon,
                    latitude = lat
                )
            } catch (e: HttpException) {
                if (e.code() == 429) continue else throw e
            }
        }
        return null
    }

    suspend fun geocodeAddress(address: String): Pair<Double, Double>? {
        for (apiKey in apiKeys) {
            try {
                val response = RetrofitKakaoInstance.api.searchAddress(apiKey, address)
                val doc = response.documents.firstOrNull()
                return if (doc != null) Pair(doc.y.toDouble(), doc.x.toDouble()) else null
            } catch (e: HttpException) {
                if (e.code() == 429) continue else throw e
            }
        }
        return null
    }
}
