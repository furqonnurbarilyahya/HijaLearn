package com.bangkit.hijalearn

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: WelcomeRepository): ViewModel() {

    private val mutableStateFlow = MutableStateFlow(true)
    val isLoading = mutableStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            mutableStateFlow.value = false
        }
    }

    fun getSession() : Flow<User> {
        return repository.getSession()
    }

}