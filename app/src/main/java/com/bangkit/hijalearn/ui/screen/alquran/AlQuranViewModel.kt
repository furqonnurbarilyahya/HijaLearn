package com.bangkit.hijalearn.ui.screen.alquran

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.launch

class AlQuranViewModel(private val repository: MainRepository): ViewModel(){

    val listSurahState = repository.listSurahState

    fun getListSurah() {
        viewModelScope.launch {
            repository.getListSurah()
        }
    }
}