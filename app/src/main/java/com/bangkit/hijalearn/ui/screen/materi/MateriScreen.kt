package com.bangkit.hijalearn.ui.screen.materi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.ui.component.MateriDialog
import kotlinx.coroutines.runBlocking

@Composable
fun MateriScreen (
    context: Context,
    nomor: Int,
    modulId: Int,
    namaModul: String,
    onPrevNextMateriClick: (Int,Int,String) -> Unit,
    onClickBack: () -> Unit,
    viewModel: MateriViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {
    var isRecording by remember {
        mutableStateOf(false)
    }

    var isCorrect by remember {
        mutableStateOf(false)
    }

    var isIncorrect by remember {
        mutableStateOf(false)
    }

    var isError by remember {
        mutableStateOf(false)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }
    
    var showDialog by remember {
        mutableStateOf(false)
    }

    val totalCompleted by viewModel.totalCompleted.collectAsState()

//    val predictionResult by viewModel.predictionResult.collectAsState()
    viewModel.predictionResult.collectAsState().value.let {
        when (it) {
            is Result.Success -> {
                if (it.data.correct) {
                    isCorrect = true
                    viewModel.getSingleProgress(modulId)
                } else {
                    isIncorrect = true
                }
                isLoading = false
                viewModel.resetResult()
            }
            is Result.Error -> {
                it.error.getContentIfNotHandled()?.let {
                    isError = true
                }
                isLoading = false
                viewModel.resetResult()
            }
            is Result.Loading -> {
                isLoading = it.isLoading
            }
        }
    }

    
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
            if (isGranted) {
                // Start
                Toast.makeText(context,"Permission granted",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context,"Permission denied",Toast.LENGTH_SHORT).show()
            }
        }


    fun checkPermissionAndStartStopRecording(caraEja: String, done: Boolean) {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) -> {

                if (isRecording) {
                    viewModel.stopRecording(caraEja, done, modulId)
                    isRecording = false
                } else {
                    viewModel.audioRecord = AudioRecord(
                        MediaRecorder.AudioSource.MIC,
                        MateriViewModel.RECORDER_SAMPLE_RATE,
                        AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        512
                    )

                    viewModel.startRecording(context = context)
                    isRecording = true
                }
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
    }
    
    DisposableEffect(isLoading, isCorrect, isIncorrect, isError) {
        onDispose {
            showDialog = isLoading || isCorrect || isIncorrect || isError
        }
    }


    if (showDialog) {
        MateriDialog(
            isLoading = isLoading,
            isCorrect = isCorrect,
            isIncorrect = isIncorrect,
            isError = isError,
            onOkayClick = {
                runBlocking {
                    isLoading  = false
                    isCorrect = false
                    isIncorrect = false
                    isError = false
                    showDialog = false
                }
            }
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            IconButton(
                onClick = onClickBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                text = "$namaModul ke-$nomor",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        viewModel.materiState.collectAsState(initial = UiState.Loading).value.let {
            when (it) {
                is UiState.Loading -> {
                    viewModel.getMateriByNomorAndModulId(nomor, modulId)
                }
                is UiState.Success -> {
                    val data = it.data
                    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
                    Spacer(modifier = Modifier.height(26.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 38.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = data.namaMateri,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(42.dp))
                            AsyncImage(
                                model = data.namaGambar,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(170.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = data.caraEja,
                                fontSize = 24.sp,
                                fontStyle = FontStyle.Italic
                            )
                            Spacer(modifier = Modifier.height(90.dp))
                            Image(
                                imageVector = Icons.Filled.PlayCircle,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.Green),
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        mediaPlayer?.release()
                                        mediaPlayer = MediaPlayer().apply {
                                            setDataSource(data.namaAudio)
                                            prepare()
                                            start()
                                        }
                                    },
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Klik untuk belajar",
                                fontStyle = FontStyle.Italic
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 32.dp)
                            .weight(0.15f)
                    ) {
                        val sudahPernah = nomor <= (totalCompleted ?: 0)
                        if (sudahPernah && nomor != 29) {
                            IconButton(
                                onClick = { onPrevNextMateriClick(nomor+1,modulId,namaModul) },
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .wrapContentSize()
                                    .padding(end = 24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowCircleRight,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                        if (nomor != 1) {
                            IconButton(
                                onClick = { onPrevNextMateriClick(nomor-1,modulId,namaModul) },
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .wrapContentSize()
                                    .padding(start = 24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowCircleLeft,
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp),
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                        Button(
                            onClick = {
                                      checkPermissionAndStartStopRecording(data.caraEja.lowercase(), nomor <= totalCompleted!!)
                            },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .height(40.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 100.dp),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 10.dp
                            )
                        ) {
                            if (isRecording) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.StopCircle,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Berhenti Record",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            } else {
                                Row(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Mic,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Giliran kamu",
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Gagal memuat")
                            Button(onClick = { viewModel.getMateriByNomorAndModulId(nomor, modulId) }) {
                                Text(text = "Coba lagi")
                            }
                        }
                    }
                }
            }

        }
    }
}

