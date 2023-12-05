package com.bangkit.hijalearn.ui.screen.list_materi

import androidx.lifecycle.ViewModel
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.model.Materi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ListMateriViewModel(private val repository: MainRepository): ViewModel() {
    fun getAllMateriByModulId(modulId: Int) = repository.getAllMateriByModul(modulId)
    fun countMateriSelesaiByModul(modulId: Int) = repository.countMateriSelesaiByModul(modulId)
}