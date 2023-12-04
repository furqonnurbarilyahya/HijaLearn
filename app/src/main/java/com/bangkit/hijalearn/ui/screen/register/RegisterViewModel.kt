package com.bangkit.hijalearn.ui.screen.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.data.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: WelcomeRepository): ViewModel() {
    val registerResult = repository.registerResult
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var isUsernameEmpty by mutableStateOf(false)
    var isEmailEmpty by mutableStateOf(false)
    var isEmailNotValid by mutableStateOf(false)
    var isPasswordEmpty by mutableStateOf(false)
    var isPasswordNotValid by mutableStateOf(false)
    var showDialog by mutableStateOf(false)
    fun onValueUsernameChange(newUsername: String) {
        username = newUsername
    }
    fun onValueEmailChange(newEmail: String) {
        email = newEmail
    }

    fun onValuePasswordChange(newPassword: String) {
        password = newPassword
    }

    fun onClickTrailingIcon() {
        isPasswordVisible = !isPasswordVisible
    }
    fun register(email: String, password: String, username: String) {
        viewModelScope.launch { repository.register(email, password, username) }
    }

    fun resetLoading() {
        repository.resetRegisterLoading()
    }

    fun showSuccessDialog(moveToLoginScreen: () -> Unit) {
        viewModelScope.launch {
            showDialog = true
            delay(2000)
            showDialog = false
            moveToLoginScreen()
        }
    }

}