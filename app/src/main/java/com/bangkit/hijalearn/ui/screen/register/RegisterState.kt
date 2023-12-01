package com.bangkit.hijalearn.ui.screen.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RegisterState {
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)

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
}