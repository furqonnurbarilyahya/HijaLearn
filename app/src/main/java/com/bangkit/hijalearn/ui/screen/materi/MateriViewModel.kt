package com.bangkit.hijalearn.ui.screen.materi

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Materi
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MateriViewModel(private val repository: MainRepository): ViewModel() {
    val materiState = repository.materiState
    val totalCompleted = repository.totalCompleted
    val getProgressLoading = repository.getProgressLoading
    val updateProgressLoading = repository.updateProgressLoading
    fun getMateriByNomorAndModulId(nomor: Int,modulId: Int) {
        viewModelScope.launch {
            repository.getMateriByNomorAndModulId(nomor,modulId)
        }
    }

    fun updateProgress(modulId: Int) {
        viewModelScope.launch {
            repository.updateProgress(modulId)
        }
    }
}