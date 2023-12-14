package com.bangkit.hijalearn.ui.screen.profile

import androidx.lifecycle.ViewModel
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.flow.Flow

class ProfileViewModel(private val repository: MainRepository): ViewModel() {
    fun getSession(): Flow<User> {
        return repository.getSession()
    }
}