package com.bangkit.hijalearn.data.remote.repository

import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.remote.retrofit.ApiService
import com.bangkit.hijalearn.model.Introduction
import com.bangkit.hijalearn.model.Materi
import com.bangkit.hijalearn.model.Module
import com.bangkit.hijalearn.model.User
import com.bangkit.hijalearn.model.dummyIntroduction
import com.bangkit.hijalearn.model.dummyMateri
import com.bangkit.hijalearn.model.dummyModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class MainRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    fun getAllModule(): List<Module> {
        return dummyModule
    }

    fun getAllMateriByModul(modulId: Int): List<Materi> {
        val fillteredMateri = dummyMateri.filter { it.modul == modulId }
        return fillteredMateri
    }

    fun countMateriSelesaiByModul(modulId: Int): Int {
        val listMateri = dummyMateri.filter { it.modul == modulId }
        return listMateri.count { it.selesai }
    }
    fun getIntroductionById(id: Int): Introduction {
        return dummyIntroduction.find {
            it.id == id
        }!!
    }

    fun getMateriById(materiId: Int): Materi {
        return dummyMateri.find { it.id == materiId }!!
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