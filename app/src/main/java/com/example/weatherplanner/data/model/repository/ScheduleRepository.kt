package com.example.weatherplanner.data.model.repository

import com.example.weatherplanner.data.model.Schedule
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

object ScheduleRepository {

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    private fun scheduleCollection() =
        auth.currentUser?.uid?.let { uid ->
            firestore.collection("users").document(uid).collection("schedules")
        } ?: throw IllegalStateException("로그인된 사용자 없음")

    fun saveSchedule(schedule: Schedule) {
        scheduleCollection().document(schedule.id).set(schedule)
    }

    fun deleteSchedule(scheduleId: String, onSuccess: () -> Unit = {}, onFailure: (Exception) -> Unit = {}) {
        scheduleCollection().document(scheduleId).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure(it) }
    }

    fun fetchSchedules(onSuccess: (List<Schedule>) -> Unit, onFailure: (Exception) -> Unit) {
        scheduleCollection().get()
            .addOnSuccessListener { snapshot ->
                val list = snapshot.documents.mapNotNull { it.toObject(Schedule::class.java) }
                onSuccess(list)
            }
            .addOnFailureListener { onFailure(it) }
    }
}


