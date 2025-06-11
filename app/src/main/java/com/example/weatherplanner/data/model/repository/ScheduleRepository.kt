package com.example.weatherplanner.data.model.repository

import com.example.weatherplanner.data.model.Schedule
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.auth.FirebaseAuth

object ScheduleRepository {

    private fun userScheduleRef() =
        FirebaseAuth.getInstance().currentUser?.uid?.let { uid ->
            Firebase.database.getReference("users").child(uid).child("schedules")
        } ?: throw IllegalStateException("로그인된 사용자 없음")

    fun saveSchedule(schedule: Schedule) {
        userScheduleRef().child(schedule.id).setValue(schedule)
    }

    fun deleteSchedule(scheduleId: String, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        userScheduleRef().child(scheduleId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun fetchSchedules(
        onSuccess: (List<Schedule>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        userScheduleRef().get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.children.mapNotNull { it.getValue(Schedule::class.java) }
                onSuccess(list)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}

