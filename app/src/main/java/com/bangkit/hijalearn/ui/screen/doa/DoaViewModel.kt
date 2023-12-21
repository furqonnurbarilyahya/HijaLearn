package com.bangkit.hijalearn.ui.screen.doa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.launch

class DoaViewModel(private val repository: MainRepository): ViewModel() {
    val listDoaState = repository.listDoaState

    fun getDoa() {
        viewModelScope.launch {
            repository.getDoa()
        }
    }
}