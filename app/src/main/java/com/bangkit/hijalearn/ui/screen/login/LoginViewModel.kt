package com.bangkit.hijalearn.ui.screen.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: WelcomeRepository): ViewModel() {
    val result = repository.loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }

    fun saveSession(user: User) {
        viewModelScope.launch{
            withContext(Dispatchers.IO) {
                repository.saveSession(user)
            }
        }
    }

    fun resetLoading() {
        repository.resetLoginLoading()
    }

}