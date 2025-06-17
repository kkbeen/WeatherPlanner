package com.example.weatherplanner.ui.setting

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SettingScreen() {
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val db = FirebaseFirestore.getInstance()

    var sliderValue by remember { mutableStateOf(40f) } // 기본값 40%

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "강우 확률 알림 기준", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "${sliderValue.toInt()}%", style = MaterialTheme.typography.bodyLarge)

        Slider(
            value = sliderValue,
            onValueChange = { newValue -> sliderValue = newValue },
            valueRange = 0f..100f,
            steps = 19,
            onValueChangeFinished = {
                uid?.let {
                    db.collection("users").document(it)
                        .update("rainThreshold", sliderValue.toInt())
                        .addOnSuccessListener {
                            Log.d("Firestore", "rainThreshold 저장: ${sliderValue.toInt()}")
                        }
                }
            },
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
