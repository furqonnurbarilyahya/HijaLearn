package com.bangkit.hijalearn.ui.screen.introduction

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Pendahuluan
import com.bangkit.hijalearn.data.local.database.PendahuluanAndModul
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IntroductionViewModel(private val repository: MainRepository): ViewModel() {
    val pendahuluanState = repository.pendahuluanState
    fun getIntroductionById(id: Int) {
        viewModelScope.launch {
            repository.getPendahuluanById(id)
        }
    }
}