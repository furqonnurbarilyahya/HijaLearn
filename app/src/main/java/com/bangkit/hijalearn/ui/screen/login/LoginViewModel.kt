package com.bangkit.hijalearn.ui.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.Dispatchers
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

    private fun isEmailNotValid(email: String): Boolean {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private fun checkInputValid() {
        isEmailEmpty = email == ""
        isPasswordEmpty = password == ""
        isEmailNotValid = isEmailNotValid(email)
    }

    fun login() {
        checkInputValid()
        loginInvalidUser = false
        if (isEmailEmpty || isEmailNotValid || isPasswordEmpty) {
            resetLoading()
        } else {
            viewModelScope.launch {
                repository.login(email, password)
            }
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