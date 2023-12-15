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
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Materi
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

class MateriViewModel(private val repository: MainRepository): ViewModel() {
    val materiState = repository.materiState
    val totalCompleted = repository.totalCompleted
    val getProgressLoading = repository.getProgressLoading
    val updateProgressLoading = repository.updateProgressLoading
    fun getMateriByNomorAndModulId(nomor: Int,modulId: Int) {
        viewModelScope.launch {
            repository.getMateriByNomorAndModulId(nomor,modulId)
        }
    }

    fun updateProgress(modulId: Int) {
        viewModelScope.launch {
            repository.updateProgress(modulId)
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

    fun stopRecording() {
        Log.d("RECORD","Stop Recording...")
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null
    }



}