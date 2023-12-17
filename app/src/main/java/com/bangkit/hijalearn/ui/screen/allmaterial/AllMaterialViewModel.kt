package com.bangkit.hijalearn.ui.screen.allmaterial

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.launch

class AllMaterialViewModel(private val repository: MainRepository): ViewModel() {

    val allModuleState = repository.allModulState
    fun getAllModule() {
        viewModelScope.launch {
            repository.getAllModule()
        }
    }
}