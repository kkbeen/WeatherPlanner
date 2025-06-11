package com.example.weatherplanner.data.model.api

import com.example.weatherplanner.data.model.KakaoSearchResponse
import com.example.weatherplanner.data.model.KakaoAddressResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApiService {
    @GET("v2/local/search/category.json")
    suspend fun searchPlacesByCategory(
        @Header("Authorization") apiKey: String,
        @Query("category_group_code") categoryCode: String,
        @Query("x") longitude: Double,
        @Query("y") latitude: Double,
        @Query("radius") radius: Int = 1000,
        @Query("size") size: Int = 15
    ): KakaoSearchResponse

    @GET("v2/local/search/address.json")
    suspend fun searchAddress(
        @Header("Authorization") apiKey: String,
        @Query("query") address: String
    ): KakaoAddressResponse
}
