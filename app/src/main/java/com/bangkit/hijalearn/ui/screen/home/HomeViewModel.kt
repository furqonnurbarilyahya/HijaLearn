package com.bangkit.hijalearn.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.model.Module
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(private val repository: MainRepository): ViewModel() {

    fun getSession() : Flow<User> {
        return repository.getSession()
    }

    private val _listModule = MutableStateFlow(
        repository.getAllModule()
    )
    val listModule: StateFlow<List<Module>> get() = _listModule
}