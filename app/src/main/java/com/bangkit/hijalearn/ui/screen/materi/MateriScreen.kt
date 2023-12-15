package com.bangkit.hijalearn.ui.screen.materi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.PlayCircle
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.WelcomeViewModelFactory
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection

@Composable
fun MateriScreen (
    context: Context,
    nomor: Int,
    modulId: Int,
    namaModul: String,
    onClickBack: () -> Unit,
    viewModel: MateriViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {
    var isRecording by remember {
        mutableStateOf(false)
    }
    
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
            if (isGranted) {
                // Start
                if (isRecording) {
                    viewModel.stopRecording()
                    isRecording = false
                } else {
                    viewModel.startRecording(context = context)
                    isRecording = true
                }
            } else {
                Toast.makeText(context,"Permisi denied",Toast.LENGTH_SHORT).show()
            }
        }


    fun checkPermissionAndStartStopRecording() {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {
                if (isRecording) {
                    viewModel.stopRecording()
                    isRecording = false
                } else {
                    viewModel.startRecording(context = context)
                    isRecording = true
                }
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }
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
                    val totalCompleted by viewModel.totalCompleted.collectAsState()
                    val getProgressLoading by viewModel.getProgressLoading.collectAsState()
                    val updateProgressLoading by viewModel.updateProgressLoading.collectAsState()
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
                                fontSize = 32.sp,
                                fontWeight = FontWeight.SemiBold
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
                        val sudahPernah = nomor < (totalCompleted ?: 0)
                        if (sudahPernah) {
                            Button(onClick = { /*TODO*/ }, modifier = Modifier.align(Alignment.BottomEnd)) {
                                Text(text = "Lanjut")
                            }
                        }
                        if (getProgressLoading || updateProgressLoading) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }


                        // BUAT TEST RECORD AUDIO
                        Button(
                            onClick = { checkPermissionAndStartStopRecording() },
                            modifier = Modifier.align(Alignment.TopStart)
                        ) {
                            if (isRecording) {
                                Text(text = "Stop Record")
                            } else {
                                Text(text = "Start Record")
                            }
                        }

                        Text(
                            text = if (viewModel.recordedFilePath.isNullOrEmpty()) "Kosong" else viewModel.recordedFilePath!!,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )

                        Button(
                            onClick = {
                                      if (viewModel.recordedFilePath.isNullOrEmpty()) {
                                          // do nothin
                                      } else {
                                          val mPlayer = MediaPlayer()
                                          mPlayer.setDataSource(viewModel.recordedFilePath)
                                          mPlayer.prepare()
                                          mPlayer.start()
                                      }
                            },
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            if (viewModel.recordedFilePath.isNullOrEmpty()) {
                                Text(text = "Disabled")
                            } else {
                                Text(text = "Play")
                            }
                        }
                        
                        Button(
                            onClick = {
                                //api.Evaluate(sudahPernah)
                                if (sudahPernah) {
                                    // Evaluate
                                    // Notify user
                                    // api.evaluate()
                                } else {
                                    // Evaluate
                                    // Notify user
                                    // api.evaluate()
                                    val response = listOf<Boolean>(true,false).random()
                                    if (response) {
                                        viewModel.updateProgress(modulId)
                                        Toast.makeText(context,"Evaluasi benar",Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context,"Evaluasi salah",Toast.LENGTH_SHORT).show()
                                    }
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .height(40.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 100.dp),
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 10.dp
                            )
                        ) {
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
                is UiState.Error -> {
                    // DO Nothing
                }
            }

        }
    }
}

