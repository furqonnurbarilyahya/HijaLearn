package com.bangkit.hijalearn.data

import com.bangkit.hijalearn.util.Event

sealed class Result<out R> private constructor(){
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: Event<String>) : Result<Nothing>()
    data class Loading(val isLoading: Boolean) : Result<Nothing>()
}