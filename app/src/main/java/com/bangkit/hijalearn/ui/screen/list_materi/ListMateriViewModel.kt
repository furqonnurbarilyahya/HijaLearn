package com.bangkit.hijalearn.ui.screen.list_materi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.launch

class ListMateriViewModel(private val repository: MainRepository): ViewModel() {
    fun countMateriSelesaiByModul(modulId: Int) = repository.countMateriSelesaiByModul(modulId)

    val listMateriState = repository.listMateriState

    val singleProgress = repository.singleProgressState

    fun getAllMateriByModulId(modulId: Int) {
        viewModelScope.launch {
            repository.getAllMateriByModulId(modulId)
        }
    }

    fun getSingleProgress(modulId: Int) {
        viewModelScope.launch {
            repository.getSingleProgress(modulId)
        }
    }


}