package com.bangkit.hijalearn.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.local.database.Modul
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: MainRepository): ViewModel() {
    val progessState = repository.progressState
    val allModulState = repository.allModulState
    fun getProgress() {
        viewModelScope.launch {
            repository.getProgress()
        }
    }
    fun getAllModul() {
        viewModelScope.launch {
            repository.getAllModule()
        }
    }
    fun getSession() : Flow<User> {
        return repository.getSession()
    }


}