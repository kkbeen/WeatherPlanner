package com.example.weatherplanner.ui.map

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    Text(text = "🗺️ Map Screen")
}

@Preview
@Composable
private fun MapScreenPreview() {
    MapScreen()
}