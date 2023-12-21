package com.bangkit.hijalearn.ui.screen.list_materi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Materi
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListMateriViewModel(private val repository: MainRepository): ViewModel() {
    // UiState list materi
    private val _listMateriState: MutableStateFlow<UiState<List<Materi>>> = MutableStateFlow(UiState.Loading)
    val listMateriState: StateFlow<UiState<List<Materi>>> get() = _listMateriState

    val singleProgress = repository.singleProgressState

    fun getAllMateriByModulId(modulId: Int) {
        viewModelScope.launch {
            try {
                _listMateriState.value = UiState.Success(repository.getAllMateriByModulId(modulId))
            } catch (e: Exception) {
                _listMateriState.value = UiState.Error(e.message.toString())
            }
        }
    }

    fun getSingleProgress(modulId: Int) {
        viewModelScope.launch {
            repository.getSingleProgress(modulId)
        }
    }


}