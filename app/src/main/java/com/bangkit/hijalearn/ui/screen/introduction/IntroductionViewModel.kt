package com.bangkit.hijalearn.ui.screen.introduction

import androidx.lifecycle.ViewModel
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.model.Introduction
import com.bangkit.hijalearn.model.Module
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class IntroductionViewModel(private val repository: MainRepository): ViewModel() {

    fun getIntroductionById(id: Int): Introduction {
        return repository.getIntroductionById(id)
    }
}