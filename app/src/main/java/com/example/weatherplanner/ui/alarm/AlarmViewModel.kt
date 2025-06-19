package com.example.weatherplanner.ui.alarm

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AlarmViewModel : ViewModel() {
    private val _alarms = mutableStateListOf<AlarmInfo>()
    val alarms: List<AlarmInfo> = _alarms

    fun loadAlarms(uid: String) {
        FirebaseFirestore.getInstance()
            .collection("users").document(uid)
            .collection("alarms")
            .get()
            .addOnSuccessListener { result ->
                _alarms.clear()
                for (doc in result) {
                    val alarm = doc.toObject(AlarmInfo::class.java)
                    _alarms.add(alarm)
                }
            }
            .addOnFailureListener {
                Log.e("AlarmViewModel", "불러오기 실패", it)
            }
    }

    fun deleteAlarm(uid: String, alarmId: String) {
        FirebaseFirestore.getInstance()
            .collection("users").document(uid)
            .collection("alarms")
            .whereEqualTo("id", alarmId)
            .get()
            .addOnSuccessListener { result ->
                for (doc in result) {
                    doc.reference.delete()
                }
                _alarms.removeAll { it.id == alarmId }
            }
    }
}
