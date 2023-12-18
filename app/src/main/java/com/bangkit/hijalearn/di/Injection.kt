package com.bangkit.hijalearn.di

import android.content.Context
import com.bangkit.hijalearn.data.local.database.ModuleDatabase
import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.pref.dataStore
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

object Injection {
    fun provideWelcomeRepository(context: Context): WelcomeRepository {
        val userPref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return WelcomeRepository.getInstance(apiService,userPref)
    }
    fun provideMainRepository(context: Context): MainRepository{
        val userPref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getAuthApiService()
        val alQuranApiService = ApiConfig.getAlquranApiService()
        val moduleDatabase = ModuleDatabase.getDatabase(context, CoroutineScope(SupervisorJob()))
        return MainRepository.getInstance(apiService,userPref,moduleDatabase, alQuranApiService)
    }


}