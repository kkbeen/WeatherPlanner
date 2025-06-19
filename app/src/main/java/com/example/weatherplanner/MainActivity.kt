package com.example.weatherplanner

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherplanner.navigation.NavigationGraph
import com.example.weatherplanner.navigation.Routes
import com.example.weatherplanner.ui.component.BottomNavigationBar
import com.example.weatherplanner.ui.theme.WeatherPlannerTheme
import com.example.weatherplanner.viewmodel.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener { tokenTask ->
            if (tokenTask.isSuccessful) {
                val token = tokenTask.result
                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@addOnCompleteListener
                val data = hashMapOf("fcmToken" to token)
                FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .set(data, SetOptions.merge())
            }
        }
        enableEdgeToEdge()
        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val notificationPermissionState = rememberMultiplePermissionsState(
                    listOf(android.Manifest.permission.POST_NOTIFICATIONS)
                )
                LaunchedEffect(Unit) {
                    notificationPermissionState.launchMultiplePermissionRequest()
                }
            }

            WeatherPlannerTheme {
//                val navController = rememberNavController()
//                Scaffold(
//                    bottomBar = { BottomNavigationBar(navController) }
//                ) { innerPadding ->
//                    Box(modifier = Modifier.padding(innerPadding)) {
//                        NavigationGraph(navController = navController)
//                    }
//                }
                val weatherViewModel: WeatherViewModel = viewModel()
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute by remember(navBackStackEntry) {
                    derivedStateOf {
                        Routes.getRoute(navBackStackEntry?.destination?.route)
                    }
                }

                Scaffold(
                    bottomBar = {
                        if (currentRoute.isRoot) {
                            BottomNavigationBar(navController)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavigationGraph(
                            navController = navController,
                            weatherViewModel = weatherViewModel
                        )
                    }
                }
            }
        }
    }
}