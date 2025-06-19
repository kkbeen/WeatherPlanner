package com.example.weatherplanner.ui.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.weatherplanner.R
import com.google.firebase.auth.FirebaseAuth
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherplanner.navigation.Routes

data class AlarmInfo(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val time: String = "",
    val date: String = ""
)

@Composable
fun AlarmScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController? = null,
    viewModel: AlarmViewModel = viewModel()
) {
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
    val alarms = viewModel.alarms

    LaunchedEffect(uid) {
        viewModel.loadAlarms(uid)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color(0xFF3B5998), // 전체 툴바 배경을 파란색으로
                title = {
                    Text(
                        text = "알람 목록",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_arrow_back_ios_new_24),
                            contentDescription = "뒤로가기"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
        ) {
            items(alarms.size) { index ->
                val alarm = alarms[index]
                AlarmBox(
                    alarm = alarm,
                    onAlarmClick = {
                        navController?.navigate(Routes.Schedule.route) {
                            popUpTo(0) { inclusive = false } // 전체 스택 클리어
                            launchSingleTop = true
                        }
                    },
                    onDeleteClick = { alarmId -> viewModel.deleteAlarm(uid, alarmId) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}


