package com.example.weatherplanner.data.model

import java.util.UUID

data class Schedule(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val date: String = "",
    val time: String = "",
    val location: String = ""
)