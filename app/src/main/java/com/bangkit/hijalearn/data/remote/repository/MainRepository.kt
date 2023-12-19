package com.bangkit.hijalearn.data.remote.repository

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Materi
import com.bangkit.hijalearn.data.local.database.Modul
import com.bangkit.hijalearn.data.local.database.ModuleDatabase
import com.bangkit.hijalearn.data.local.database.Pendahuluan
import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.remote.response.ProgressResponse
import com.bangkit.hijalearn.data.remote.response.SingleModuleProgressResponse
import com.bangkit.hijalearn.data.remote.retrofit.ApiService
import com.bangkit.hijalearn.model.ListSurahResponseItem
import com.bangkit.hijalearn.model.SurahResponse
import com.bangkit.hijalearn.model.SurahResponseItem
import com.bangkit.hijalearn.model.User
import com.bangkit.hijalearn.model.dummyMateri
import com.bangkit.hijalearn.util.Event
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call

class MainRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val moduleDatabase: ModuleDatabase,
    private val alQuranApiService: ApiService
) {
    private val moduleDao = moduleDatabase.moduleDao()

    // UiState for progress
    private var _progressState = MutableStateFlow<UiState<ProgressResponse>>(UiState.Loading)
    val progressState : StateFlow<UiState<ProgressResponse>> get() = _progressState

    // UiState for SingleModuleProgress
    private var _singleProgressState = MutableStateFlow<UiState<SingleModuleProgressResponse>>(UiState.Loading)
    val singleProgressState : StateFlow<UiState<SingleModuleProgressResponse>> get() = _singleProgressState

    // UiState list modul
    private var _allModulState = MutableStateFlow<UiState<List<Modul>>>(UiState.Loading)
    val allModulState : StateFlow<UiState<List<Modul>>> get() = _allModulState

    // UiState pendahuluan
    private val _pendahuluanState: MutableStateFlow<UiState<Pendahuluan>> = MutableStateFlow(UiState.Loading)
    val pendahuluanState: StateFlow<UiState<Pendahuluan>> get() = _pendahuluanState

    // UiState list materi
    private val _listMateriState: MutableStateFlow<UiState<List<Materi>>> = MutableStateFlow(UiState.Loading)
    val listMateriState: StateFlow<UiState<List<Materi>>> get() = _listMateriState

    // UiState materi
    private val _materiState: MutableStateFlow<UiState<Materi>> = MutableStateFlow(UiState.Loading)
    val materiState: StateFlow<UiState<Materi>> get() = _materiState
    fun getSession(): Flow<User> {
        return userPreference.getSession()
    }

    suspend fun getAllModule() {
        try {
            _allModulState.value = UiState.Success(moduleDao.getAllModul())
        } catch (e: Exception) {
            _allModulState.value = UiState.Error(e.message.toString())
        }
    }

    fun countMateriSelesaiByModul(modulId: Int): Int {
        val listMateri = dummyMateri.filter { it.modul == modulId }
        return listMateri.count { it.selesai }
    }
    suspend fun getPendahuluanById(modulId: Int) {
        try {
            _pendahuluanState.value = UiState.Success(moduleDao.getPendahuluanWithModulById(modulId))
        } catch (e: Exception) {
            _pendahuluanState.value = UiState.Error(e.message.toString())
        }
    }

    suspend fun getAllMateriByModulId(modulId: Int)  {
        try {
            _listMateriState.value = UiState.Success(moduleDao.getListMateriByModulId(modulId))
        } catch (e: Exception) {
            _listMateriState.value = UiState.Error(e.message.toString())
        }
    }

    suspend fun getMateriByNomorAndModulId(nomor: Int,modulId: Int) {
        try {
            _materiState.value = UiState.Success(moduleDao.getMateriByNomorAndModulId(nomor,modulId))
        } catch (e: Exception) {
            _materiState.value = UiState.Error(e.message.toString())
        }
    }

    // DUMMY PROGRESS LOGIC
    var dummyProgressResponse =
        TesProgressResponse(
            1,
            listOf(
                TesModulProgress(
                    1,
                    false,
                    28,
                    1
                ),
                TesModulProgress(
                    2,
                    false,
                    3,
                    1
                ),
                TesModulProgress(
                    3,
                    false,
                    3,
                    1
                )
            )
        )
    val totalCompleted = MutableStateFlow<Int?>(null)

    suspend fun getProgress() {
        _progressState.value = UiState.Loading
        try {
            val success = UiState.Success(apiService.getProgress())
            _progressState.value = success
        } catch (e : Exception) {
            _progressState.value = UiState.Error("Something error")
        }
    }

    private val _predictionResult = MutableStateFlow<Result<String>>(Result.Loading(false))
    val predictionResult: StateFlow<Result<String>> get() = _predictionResult
    suspend fun postPrediction(
        audioFile: MultipartBody.Part,
        caraEja: RequestBody,
        moduleId: RequestBody,
        done: RequestBody
    ): String {
        return apiService.postPrediction(audioFile, caraEja, moduleId, done)
    }

    suspend fun getSingleProgress(modulId: Int) {
        try {
            val success = apiService.getSingleProgress(modulId)
            _singleProgressState.value = UiState.Success(success)
            totalCompleted.value = success.subModuleDone
        } catch (e: Exception) {
            _singleProgressState.value = UiState.Error("Terjadi kesalahan")
        }
    }

    //UIState List Surah
    private val _listSurahState: MutableStateFlow<UiState<List<ListSurahResponseItem>>> = MutableStateFlow(UiState.Loading)
    val listSurahState: StateFlow<UiState<List<ListSurahResponseItem>>> get() = _listSurahState

    suspend fun getListSurah() {
        try {
            _listSurahState.value = UiState.Success(alQuranApiService.getListSurah())
        } catch (e: Exception) {
            _listSurahState.value = UiState.Error(e.message.toString())
        }
        Log.d("SURAH", "${_listSurahState.value.toString()}")
    }
    
    //UIState List Ayat per Surah
    private val _listAyatState: MutableStateFlow<UiState<List<SurahResponseItem>>> = MutableStateFlow(UiState.Loading)
    val listAyatState: StateFlow<UiState<List<SurahResponseItem>>> get() = _listAyatState

    suspend fun getSurahById(id: String) {
        try {
            _listAyatState.value = UiState.Success(alQuranApiService.getSurahById(id))
        } catch (e: Exception) {
            _listAyatState.value = UiState.Error(e.message.toString())
        }
        Log.d("AYAT", "${_listAyatState.value.toString()}")
    }

    data class TesModulProgress(
        val modulId: Int,
        val completed: Boolean = false,
        val totalSubmodul: Int,
        var totalCompletedSubmodul: Int
    )
    data class TesProgressResponse(
        var lastModulAccesId: Int,
        val modulProgress: List<TesModulProgress>
    )

    companion object {
        @Volatile
        private var instance: MainRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference,
            moduleDatabase: ModuleDatabase,
            alQuranApiService: ApiService

        ): MainRepository =
            instance ?: synchronized(this) {
                instance ?: MainRepository(apiService, userPreference,moduleDatabase, alQuranApiService)
            }.also { instance = it }
    }
}