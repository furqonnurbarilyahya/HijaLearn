package com.bangkit.hijalearn.ui.screen.introduction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.launch

class IntroductionViewModel(private val repository: MainRepository): ViewModel() {
    val pendahuluanState = repository.pendahuluanState
    fun getIntroductionById(id: Int) {
        viewModelScope.launch {
            repository.getPendahuluanById(id)
        }
    }
}