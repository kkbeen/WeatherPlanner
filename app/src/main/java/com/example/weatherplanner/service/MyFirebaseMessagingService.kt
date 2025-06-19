package com.example.weatherplanner.service

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "New token: $token")
        saveTokenToFirestore(token)
    }

    private fun saveTokenToFirestore(token: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        val data = hashMapOf("fcmToken" to token)

        db.collection("users").document(uid).set(data, SetOptions.merge())
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FCM", "메시지 수신: ${remoteMessage.data}")

        val title = remoteMessage.notification?.title ?: "알림"
        val body = remoteMessage.notification?.body ?: ""
        val now = System.currentTimeMillis()
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now)
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now)

        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val db = FirebaseFirestore.getInstance()

        val newAlarm = hashMapOf(
            "id" to UUID.randomUUID().toString(),
            "time" to time,
            "date" to date,
            "title" to title,
            "message" to body
        )

        db.collection("users").document(uid)
            .collection("alarms")
            .add(newAlarm)
            .addOnSuccessListener {
                Log.d("FCM", "알림 저장 성공")
            }
            .addOnFailureListener {
                Log.e("FCM", "알림 저장 실패", it)
            }
    }


}