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

    init {
        loadSchedulesFromFirebase()
    }

    // 일정 추가
    fun addSchedule(schedule: Schedule) {
        ScheduleRepository.saveSchedule(schedule) // Firebase에 저장
        loadSchedulesFromFirebase() // 저장 후 전체 동기화 (다시 불러오기)
    }

    // 일정 삭제
    fun removeSchedule(id: String) {
        // 리스트 직접 삭제 X!
        ScheduleRepository.deleteSchedule(id,
            onSuccess = {
                Log.d("Schedule", "삭제 성공")
                loadSchedulesFromFirebase() // 성공 후 동기화!
            },
            onFailure = { e -> Log.e("Schedule", "삭제 실패", e) }
        )
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

    fun updateSchedule(updated: Schedule) {
        // 리스트 직접 수정 X!
        ScheduleRepository.saveSchedule(updated)
        loadSchedulesFromFirebase() // 저장 후 동기화!
    }

}