package com.example.weatherplanner.ui.schedule

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.weatherplanner.data.model.Schedule
import com.example.weatherplanner.data.model.repository.ScheduleRepository

class ScheduleViewModel : ViewModel() {

    // 로컬 상태로 일정 리스트 저장
    private val _scheduleList = mutableStateListOf<Schedule>()
    val scheduleList: List<Schedule> get() = _scheduleList

    // 일정 추가
    fun addSchedule(schedule: Schedule) {
        _scheduleList.add(schedule)
    }

    // (선택) 일정 삭제
    fun removeSchedule(id: String) {
        _scheduleList.removeAll { it.id == id }
    }

    fun loadSchedulesFromFirebase() {
        ScheduleRepository.fetchSchedules(
            onSuccess = { list ->
                _scheduleList.clear()
                _scheduleList.addAll(list)
            },
            onFailure = { e ->
                Log.e("Firebase", "❌ 일정 불러오기 실패", e)
            }
        )
    }
}