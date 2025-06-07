package com.example.weatherplanner.ui.map.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    locationSource: LocationSource?,
    isLocationEnabled: Boolean
) {
    NaverMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        locationSource = locationSource,
        properties = MapProperties(
            locationTrackingMode = if (isLocationEnabled) {
                LocationTrackingMode.Follow
            } else {
                LocationTrackingMode.None
            }
        ),
        uiSettings = MapUiSettings(
            isLocationButtonEnabled = isLocationEnabled
        )
    )
}