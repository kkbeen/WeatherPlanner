package com.example.weatherplanner.data.model

data class LoginUiState(
    val isError: Boolean = false,
    val success: Boolean = false,
    val message: String = ""
)
