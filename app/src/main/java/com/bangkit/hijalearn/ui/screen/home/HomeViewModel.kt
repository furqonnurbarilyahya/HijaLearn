package com.bangkit.hijalearn.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
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

}