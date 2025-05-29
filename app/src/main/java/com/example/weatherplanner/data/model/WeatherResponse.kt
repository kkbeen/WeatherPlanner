package com.example.weatherplanner.data.model

data class WeatherApiResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

data class Location(val name: String, val localtime: String)

data class Current(
    val temp_c: Double,
    val condition: Condition,
    val humidity: Int
)

data class Forecast(
    val forecastday: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val hour: List<Hourly>
)

data class Hourly(
    val time: String,
    val temp_c: Double,
    val chance_of_rain: Int,
    val condition: Condition
)

data class Condition(
    val text: String,
    val icon: String
)
