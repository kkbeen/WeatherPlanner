package com.example.weatherplanner.data.model.repository

import com.example.weatherplanner.data.model.Schedule
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore

object ScheduleRepository {

    private val db = Firebase.database
    private val ref = db.getReference("schedules")

    fun saveSchedule(schedule: Schedule) {
        ref.child(schedule.id).setValue(schedule)
    }

    fun deleteSchedule(scheduleId: String, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        ref.child(scheduleId).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }

    fun fetchSchedules(
        onSuccess: (List<Schedule>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        ref.get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.children.mapNotNull { it.getValue(Schedule::class.java) }
                onSuccess(list)
            }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
