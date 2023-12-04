package com.bangkit.hijalearn.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: MainRepository): ViewModel() {

    fun getSession() : Flow<User> {
        return repository.getSession()
    }
}