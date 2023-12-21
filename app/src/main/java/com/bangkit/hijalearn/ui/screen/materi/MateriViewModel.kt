package com.bangkit.hijalearn.ui.screen.materi

import android.content.Context
import android.media.AudioRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.data.remote.response.PredictionResponse
import com.bangkit.hijalearn.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.and
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import kotlin.concurrent.thread

class MateriViewModel(private val repository: MainRepository): ViewModel() {
    private val _predictionResult = MutableStateFlow<Result<PredictionResponse>>(Result.Loading(false))
    val predictionResult: StateFlow<Result<PredictionResponse>> get() = _predictionResult
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

    var record = false

    private var tempFile: File? = null

    var audioRecord: AudioRecord? = null

    var recordingThread: Thread? = null

    fun startRecording(context: Context) {

        tempFile = File.createTempFile("audioToEval",".wav",context.externalCacheDir)

        audioRecord?.startRecording()

        record = true

        recordingThread = thread(true) {
            writeAudioDataToFile(audioRecord,tempFile?.absolutePath ?: "")
        }
    }

    private fun wavFileHeader(): ByteArray {
        val headerSize = 44
        val header = ByteArray(headerSize)

        header[0] = 'R'.code.toByte()
        header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte()
        header[3] = 'F'.code.toByte()

        header[4] = (0 and 0xff).toByte()
        header[5] = (0 shr 8 and 0xff).toByte()
        header[6] = (0 shr 16 and 0xff).toByte()
        header[7] = (0 shr 24 and 0xff).toByte()

        header[8] = 'W'.code.toByte()
        header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte()
        header[11] = 'E'.code.toByte()

        header[12] = 'f'.code.toByte()
        header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte()
        header[15] = ' '.code.toByte()

        header[16] = 16
        header[17] = 0
        header[18] = 0
        header[19] = 0

        header[20] = 1
        header[21] = 0

        header[22] = NUMBER_CHANNELS.toByte()
        header[23] = 0

        header[24] = (RECORDER_SAMPLE_RATE and 0xff).toByte()
        header[25] = (RECORDER_SAMPLE_RATE shr 8 and 0xff).toByte()
        header[26] = (RECORDER_SAMPLE_RATE shr 16 and 0xff).toByte()
        header[27] = (RECORDER_SAMPLE_RATE shr 24 and 0xff).toByte()

        header[28] = (BYTE_RATE and 0xff).toByte()
        header[29] = (BYTE_RATE shr 8 and 0xff).toByte()
        header[30] = (BYTE_RATE shr 16 and 0xff).toByte()
        header[31] = (BYTE_RATE shr 24 and 0xff).toByte()

        header[32] = (NUMBER_CHANNELS * BITS_PER_SAMPLE / 8).toByte()
        header[33] = 0

        header[34] = BITS_PER_SAMPLE.toByte()
        header[35] = 0

        header[36] = 'd'.code.toByte()
        header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte()
        header[39] = 'a'.code.toByte()

        header[40] = (0 and 0xff).toByte()
        header[41] = (0 shr 8 and 0xff).toByte()
        header[42] = (0 shr 16 and 0xff).toByte()
        header[43] = (0 shr 24 and 0xff).toByte()

        return header
    }

    private fun short2byte(sData: ShortArray): ByteArray {
        val arrSize = sData.size
        val bytes = ByteArray(arrSize * 2)
        for (i in 0 until arrSize) {
            bytes[i * 2] = (sData[i] and 0x00FF).toByte()
            bytes[i * 2 + 1] = (sData[i].toInt() shr 8).toByte()
            sData[i] = 0
        }
        return bytes
    }

    private fun updateHeaderInformation(data: ArrayList<Byte>) {
        val fileSize = data.size
        val contentSize = fileSize - 44

        data[4] = (fileSize and 0xff).toByte()
        data[5] = (fileSize shr 8 and 0xff).toByte()
        data[6] = (fileSize shr 16 and 0xff).toByte()
        data[7] = (fileSize shr 24 and 0xff).toByte()

        data[40] = (contentSize and 0xff).toByte()
        data[41] = (contentSize shr 8 and 0xff).toByte()
        data[42] = (contentSize shr 16 and 0xff).toByte()
        data[43] = (contentSize shr 24 and 0xff).toByte()
    }

    private fun writeAudioDataToFile(recorder: AudioRecord?,path: String) {
        val sData = ShortArray(BufferElements2Rec)
        var os: FileOutputStream? = null
        try {
            os = FileOutputStream(path)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        val data = arrayListOf<Byte>()

        for (byte in wavFileHeader()) {
            data.add(byte)
        }

        while (record) {
            recorder?.read(sData, 0, BufferElements2Rec)
            try {
                val bData = short2byte(sData)
                for (byte in bData)
                    data.add(byte)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        updateHeaderInformation(data)

        os?.write(data.toByteArray())

        try {
            os?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopRecording(caraEja: String, done: Boolean, modulId: Int) {
        audioRecord?.run {
            try {
                record = false
                stop()
                release()
                recordingThread?.join()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                recordingThread = null
                audioRecord = null
            }
        }
        postPrediction(caraEja,done,modulId)
    }

    companion object {
        const val RECORDER_SAMPLE_RATE = 44100
        const val BITS_PER_SAMPLE: Short = 16
        const val NUMBER_CHANNELS: Short = 1
        const val BYTE_RATE = RECORDER_SAMPLE_RATE * NUMBER_CHANNELS * 16 / 8

        var BufferElements2Rec = 1024
    }

    private fun postPrediction(caraEja: String, done: Boolean, moduleId: Int) {
        _predictionResult.value = Result.Loading(true)
        viewModelScope.launch {
            try {
                val requestAudioFile = tempFile?.asRequestBody("audio/wav".toMediaType())
                val requestCaraEja = caraEja.toRequestBody("text/plain".toMediaType())
                val requestDone = done.toString().toRequestBody("text/plain".toMediaType())
                val requestModuleId = moduleId.toString().toRequestBody("text/plain".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "audio",
                    tempFile!!.name,
                    requestAudioFile!!
                )

                val success = repository.postPrediction(multipartBody,requestCaraEja,requestModuleId,requestDone)
                _predictionResult.value = Result.Success(success)
            } catch (e: Exception) {
                _predictionResult.value = Result.Error(Event("Terjadi kesalahan"))
            }
        }
    }

    fun resetResult() {
        _predictionResult.value = Result.Loading(false)
    }

}