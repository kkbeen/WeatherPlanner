package com.example.weatherplanner.data.model.algorithm

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

@Composable
fun LocationFetcher(onLocationObtained: (Double, Double) -> Unit) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            val locationClient = LocationServices.getFusedLocationProviderClient(context)
            locationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    onLocationObtained(it.latitude, it.longitude)
                }
            }
        } else {
            // 권한이 없으면 안내 텍스트 표시(Compose 밖 권한 요청 UI 별도 구현 필요)
            // 또는 권한 요청 로직 구현 (Activity에서 권한 요청 권장)
        }
    }

    // 권한 없을 때 안내 UI 예시 (간단 텍스트)
    val hasPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    if (!hasPermission) {
        Text("위치 권한이 필요합니다. 설정에서 권한을 허용해 주세요.")
    }
}

