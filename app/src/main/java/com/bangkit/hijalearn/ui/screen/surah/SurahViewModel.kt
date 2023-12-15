package com.bangkit.hijalearn.ui.screen.surah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.launch

class SurahViewModel(private val repository: MainRepository): ViewModel() {
    val listAyatState = repository.listAyatState

    fun getSurahById(id: String) {
        viewModelScope.launch {
            repository.getSurahById(id)
        }
    }
}