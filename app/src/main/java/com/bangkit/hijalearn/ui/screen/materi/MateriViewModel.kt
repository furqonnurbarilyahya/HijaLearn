package com.bangkit.hijalearn.ui.screen.materi

import androidx.lifecycle.ViewModel
import com.bangkit.hijalearn.data.remote.repository.MainRepository

class MateriViewModel(private val repository: MainRepository): ViewModel() {
    fun getMateriById(materiId: Int) = repository.getMateriById(materiId)
}