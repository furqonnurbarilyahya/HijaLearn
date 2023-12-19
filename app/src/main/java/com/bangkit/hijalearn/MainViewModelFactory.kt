package com.bangkit.hijalearn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.data.remote.repository.WelcomeRepository
import com.bangkit.hijalearn.ui.screen.allmaterial.AllMaterialViewModel
import com.bangkit.hijalearn.ui.screen.alquran.AlQuranViewModel
import com.bangkit.hijalearn.ui.screen.doa.DoaViewModel
import com.bangkit.hijalearn.ui.screen.home.HomeViewModel
import com.bangkit.hijalearn.ui.screen.introduction.IntroductionViewModel
import com.bangkit.hijalearn.ui.screen.list_materi.ListMateriViewModel
import com.bangkit.hijalearn.ui.screen.login.LoginViewModel
import com.bangkit.hijalearn.ui.screen.materi.MateriViewModel
import com.bangkit.hijalearn.ui.screen.register.RegisterViewModel
import com.bangkit.hijalearn.ui.screen.surah.SurahViewModel

class MainViewModelFactory(private val mainRepository: MainRepository): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(IntroductionViewModel::class.java)) {
            return IntroductionViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(ListMateriViewModel::class.java)) {
            return ListMateriViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(MateriViewModel::class.java)) {
            return MateriViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(AlQuranViewModel::class.java)) {
            return AlQuranViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(SurahViewModel::class.java)) {
            return SurahViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(AllMaterialViewModel::class.java)) {
            return AllMaterialViewModel(mainRepository) as T
        } else if (modelClass.isAssignableFrom(DoaViewModel::class.java)) {
            return DoaViewModel(mainRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}


