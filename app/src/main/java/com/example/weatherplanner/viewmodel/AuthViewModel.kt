package com.example.weatherplanner.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.weatherplanner.data.model.LoginUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState

    fun loginWithEmail(email: String, password: String, context: Context, onSuccess: () -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.value = LoginUiState(success = true)
                    onSuccess()
                } else {
                    _loginState.value = LoginUiState(isError = true, message = task.exception?.message ?: "로그인 실패")
                }
            }
    }

    fun registerWithEmail(
        name: String,
        birth: String,
        email: String,
        password: String,
        context: Context,
        onSuccess: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val userData = mapOf(
                        "name" to name,
                        "birth" to birth,
                        "email" to email
                    )

                    FirebaseDatabase.getInstance().getReference("users")
                        .child(uid)
                        .setValue(userData)
                        .addOnSuccessListener {
                            _loginState.value = LoginUiState(success = true)
                            onSuccess()
                        }
                        .addOnFailureListener {
                            _loginState.value = LoginUiState(isError = true, message = "회원 정보 저장 실패")
                        }
                } else {
                    _loginState.value = LoginUiState(isError = true, message = task.exception?.message ?: "회원가입 실패")
                }
            }
    }
}
