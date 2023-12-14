package com.bangkit.hijalearn.ui.screen.alquran

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.model.ListSurahResponseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlQuranViewModel(private val repository: MainRepository): ViewModel(){

    val listSurahState = repository.listSurahState

    fun getListSurah() {
        viewModelScope.launch {
            repository.getListSurah()
        }
    }
}