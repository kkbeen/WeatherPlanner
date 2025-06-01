package com.example.weatherplanner.data.model.network

import com.example.weatherplanner.data.model.api.KakaoApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitKakaoInstance {
    private const val BASE_URL = "https://dapi.kakao.com/"

    val api: KakaoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoApiService::class.java)
    }
}