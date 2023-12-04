package com.bangkit.hijalearn.ui.screen.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.model.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: WelcomeRepository): ViewModel() {
    val result = repository.loginResult
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var isEmailEmpty by mutableStateOf(false)
    var isEmailNotValid by mutableStateOf(false)
    var isPasswordEmpty by mutableStateOf(false)
    var loginInvalidUser by mutableStateOf(false)

    fun onValueEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onValuePasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onClickTrailingIcon() {
        isPasswordVisible = !isPasswordVisible
    }


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