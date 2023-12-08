package com.bangkit.hijalearn.ui.screen.list_materi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Materi
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListMateriViewModel(private val repository: MainRepository): ViewModel() {
    fun countMateriSelesaiByModul(modulId: Int) = repository.countMateriSelesaiByModul(modulId)

    val listMateriState = repository.listMateriState

    val totalCompleted = repository.totalCompleted

    fun getAllMateriWithModulByModulId(modulId: Int) {
        viewModelScope.launch {
            repository.getAllMateriByModulId(modulId)
            repository.getTotalCompletedSubModule(modulId)
        }
    }


}