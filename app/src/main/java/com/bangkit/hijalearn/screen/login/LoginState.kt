package com.bangkit.hijalearn.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class LoginState {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var isPasswordVisible by mutableStateOf(false)

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