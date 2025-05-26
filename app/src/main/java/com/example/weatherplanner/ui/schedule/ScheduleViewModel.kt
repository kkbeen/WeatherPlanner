package com.example.weatherplanner.ui.schedule

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.weatherplanner.data.model.Schedule

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
}