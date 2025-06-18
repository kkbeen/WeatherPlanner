package com.example.weatherplanner.data.model

import java.util.UUID

data class Schedule(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    constructor() : this("", "", "", "", "", null, null)
}