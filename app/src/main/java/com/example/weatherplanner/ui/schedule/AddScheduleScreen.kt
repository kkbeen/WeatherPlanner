package com.example.weatherplanner.ui.schedule

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.weatherplanner.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScheduleScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("일정 등록") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.outline_arrow_back_ios_new_24), contentDescription = "뒤로가기")
                    }
                }
            )
        }
    ) { innerPadding ->
        Text(
            text = "여기는 일정 등록 화면입니다.",
            modifier = Modifier.padding(innerPadding)
        )
    }
}