package com.bangkit.hijalearn.data.remote.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.remote.retrofit.ApiService
import com.bangkit.hijalearn.model.User
import kotlinx.coroutines.flow.Flow
import com.bangkit.hijalearn.util.Event
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class WelcomeRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    private val _loginResult = MutableStateFlow<Result<User>>(Result.Loading(false))
    val loginResult: StateFlow<Result<User>> get() = _loginResult

    private val _registerResult = MutableStateFlow<Result<String>>(Result.Loading(false))
    val registerResult: StateFlow<Result<String>> get() = _registerResult

    private val auth = Firebase.auth

    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun saveSession(user: User) {
        userPreference.saveSession(user)
    }

    suspend fun register(email: String, password: String, username: String) {
        _registerResult.value = Result.Loading(true)
        try {
            val response = apiService.register(email,password,username)
            Log.d("CEK",response)
            _registerResult.value =  Result.Success(response)
        } catch (e: HttpException) {
            _registerResult.value = Result.Error(Event(e.toString()))
        } catch (e: Exception) {
            _registerResult.value = Result.Error(Event("An error occurred"))
        }
    }

    fun login(email: String, password: String) {
        _loginResult.value = Result.Loading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val authUser = auth.currentUser
                    var idToken: String? = null
                    var user: User? = null
                    authUser?.getIdToken(true)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                idToken = task.result?.token
                                user = User(authUser.displayName!!,
                                    authUser.email!!, authUser.photoUrl.toString(),idToken!!,true)
                                _loginResult.value = Result.Success(user!!)
                            } else {
                                _loginResult.value = Result.Error(Event("Gagal mengambil token"))
                            }
                        }
                } else {
                    // If sign in fails, display a message to the user.
                    when  {
                        task.exception is FirebaseAuthInvalidUserException ||
                                task.exception is FirebaseAuthInvalidCredentialsException -> {
                            _loginResult.value = Result.Error(Event("Invalid User"))
                        }
                        else -> {
                            _loginResult.value = Result.Error(Event("Terjadi kesalahan"))
                        }
                    }
                }
            }
    }


    fun resetLoginLoading() {
        _loginResult.value = Result.Loading(false)
    }

    fun resetRegisterLoading() {
        _registerResult.value = Result.Loading(false)
    }

    companion object {
        @Volatile
        private var instance: WelcomeRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): WelcomeRepository =
            instance ?: synchronized(this) {
                instance ?: WelcomeRepository(apiService, userPreference)
            }.also { instance = it }
    }

}