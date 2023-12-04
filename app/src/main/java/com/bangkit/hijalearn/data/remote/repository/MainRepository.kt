package com.bangkit.hijalearn.data.remote.repository

import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.remote.retrofit.ApiService
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    companion object {
        @Volatile
        private var instance: MainRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(apiService, userPreference)
            }.also { instance = it }
    }
}