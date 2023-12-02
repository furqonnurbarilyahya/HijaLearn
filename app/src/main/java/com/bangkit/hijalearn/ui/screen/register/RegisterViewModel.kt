package com.bangkit.hijalearn.ui.screen.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.data.Result
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: WelcomeRepository): ViewModel() {
    val registerResult = repository.registerResult


    fun register(email: String, password: String, username: String) {
        viewModelScope.launch { repository.register(email, password, username) }
    }

    fun resetLoading() {
        repository.resetRegisterLoading()
    }

}