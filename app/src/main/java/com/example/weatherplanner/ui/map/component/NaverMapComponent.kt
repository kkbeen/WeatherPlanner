package com.example.weatherplanner.ui.map.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.weatherplanner.data.model.Place
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.MarkerState

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapComponent(
    modifier: Modifier = Modifier,
    cameraPositionState: CameraPositionState,
    locationSource: LocationSource?,
    isLocationEnabled: Boolean,
    places: List<Place> = emptyList(),
    onMarkerClick: (Place) -> Unit = {}
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
    ) {
        places.forEach { place ->
            Marker(
                state = MarkerState(position = LatLng(place.y.toDouble(), place.x.toDouble())),
                captionText = place.place_name,
                onClick = {
                    onMarkerClick(place)
                    true
                }
            )
        }
    }
}