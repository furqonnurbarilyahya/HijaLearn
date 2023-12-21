package com.bangkit.hijalearn.ui.screen.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
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
    var isEmailDuplicated by mutableStateOf(false)
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
    private fun isEmailNotValid(email: String): Boolean {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    fun checkInputValid() {
        isUsernameEmpty = username == ""
        isEmailEmpty = email == ""
        isPasswordEmpty = password == ""
        isEmailNotValid = isEmailNotValid(email)
        isPasswordNotValid = password.length <= 6
    }
    fun register() {
        checkInputValid()
        if (isUsernameEmpty || isEmailEmpty
            || isEmailNotValid || isPasswordEmpty
            || isPasswordNotValid
        ) {
            resetLoading()
        } else {
            viewModelScope.launch { repository.register(email, password, username) }
        }

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