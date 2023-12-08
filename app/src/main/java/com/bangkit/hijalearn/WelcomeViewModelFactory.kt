package com.bangkit.hijalearn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.ui.screen.home.HomeViewModel
import com.bangkit.hijalearn.ui.screen.introduction.IntroductionViewModel
import com.bangkit.hijalearn.ui.screen.list_materi.ListMateriViewModel
import com.bangkit.hijalearn.ui.screen.login.LoginViewModel
import com.bangkit.hijalearn.ui.screen.materi.MateriViewModel
import com.bangkit.hijalearn.ui.screen.register.RegisterViewModel

class WelcomeViewModelFactory(private val welcomRepository: WelcomeRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(welcomRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(welcomRepository) as T
        } else if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            return SplashViewModel(welcomRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}