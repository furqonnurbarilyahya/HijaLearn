package com.bangkit.hijalearn.ui.screen.materi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Materi
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MateriViewModel(private val repository: MainRepository): ViewModel() {
    private val _predictionResult = MutableStateFlow<Result<String>>(Result.Loading(false))
    val predictionResult: StateFlow<Result<String>> get() = _predictionResult
    val materiState = repository.materiState
    val totalCompleted = repository.totalCompleted
    fun getMateriByNomorAndModulId(nomor: Int,modulId: Int) {
        viewModelScope.launch {
            repository.getMateriByNomorAndModulId(nomor,modulId)
        }
    }

    fun getSingleProgress(modulId: Int) {
        viewModelScope.launch {
            repository.getSingleProgress(modulId)
        }
    }

    private var mediaRecorder: MediaRecorder? = null

    private var _recordedFilePath by mutableStateOf<String?>(null)
    val recordedFilePath: String?
        get() = _recordedFilePath

    fun startRecording(context: Context) {
        Log.d("RECORD","Start Recording...")
        val outputFolder = File(context.externalCacheDir, "audio_records")
        outputFolder.mkdirs()

        val outputFile = File.createTempFile("audioToEval_", ".wav", outputFolder)
        _recordedFilePath = outputFile.absolutePath

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            setOutputFile(recordedFilePath)
            prepare()
            start()
        }
    }

    fun postPrediction(caraEja: String,done: Boolean, moduleId: Int) {
        _predictionResult.value = Result.Loading(true)
        viewModelScope.launch {
            try {
                val audioFile = recordedFilePath?.let { File(it) }


                val requestAudioFile = audioFile?.asRequestBody("audio/wav".toMediaType())
                val requestCaraEja = caraEja.toRequestBody("text/plain".toMediaType())
                val requestDone = done.toString().toRequestBody("text/plain".toMediaType())
                val requestModuleId = moduleId.toString().toRequestBody("text/plain".toMediaType())

                val multipartBody = MultipartBody.Part.createFormData(
                    "audio",
                    audioFile!!.name,
                    requestAudioFile!!
                )

                val success = repository.postPrediction(multipartBody,requestCaraEja,requestModuleId,requestDone)
                _predictionResult.value = Result.Success(success)

            } catch (e: Exception) {
                _predictionResult.value = Result.Error(Event("Terjadi kesalahan"))
            }
        }
    }

    fun stopRecording() {
        Log.d("RECORD","Stop Recording...")
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }



}