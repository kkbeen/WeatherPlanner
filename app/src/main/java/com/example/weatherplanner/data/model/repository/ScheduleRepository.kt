package com.example.weatherplanner.data.model.repository

import com.example.weatherplanner.data.model.Schedule
import com.google.firebase.Firebase
import com.google.firebase.database.database

object ScheduleRepository {
    fun saveSchedule(schedule: Schedule) {
        val db = Firebase.database
        val ref = db.getReference("schedules").child(schedule.id)
        ref.setValue(schedule)
    }

    fun fetchSchedules(
        onSuccess: (List<Schedule>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val db = Firebase.database
        val ref = db.getReference("schedules")

        ref.get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.children.mapNotNull { it.getValue(Schedule::class.java) }
                onSuccess(list)
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }
}
