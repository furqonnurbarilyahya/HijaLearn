package com.bangkit.hijalearn.ui.screen.introduction

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.WelcomeViewModelFactory
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Pendahuluan
import com.bangkit.hijalearn.di.Injection

@Composable
fun IntroductionScreen(
    context: Context,
    id: Int,
    namaModul: String,
    desc: String,
    onClickBack: () -> Unit,
    navigateToListMateri: (Int,String,String) -> Unit,
    viewModel: IntroductionViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.End
    ) {
        // Top Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            IconButton(
                onClick =  onClickBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                text = namaModul,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        viewModel.pendahuluanState.collectAsState(initial = UiState.Loading).value.let {
            when(it) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    viewModel.getIntroductionById(id)
                }
                is UiState.Success -> {
                    val data = it.data
                    IntroductionContent(
                        data = data,
                        navigateToListMateri = navigateToListMateri,
                        namaModul = namaModul,
                        desc = desc
                    )
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Gagal memuat")
                            Button(onClick = { viewModel.getIntroductionById(id) }) {
                                Text(text = "Coba lagi")
                            }
                        }
                    }
                }
                }
            }
    }
}

@Composable
fun IntroductionContent(
    data: Pendahuluan,
    navigateToListMateri: (Int,String,String) -> Unit,
    namaModul: String,
    desc: String
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = data.title1,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.desc1,
            textAlign = TextAlign.Justify
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = data.title2,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.desc2,
            textAlign = TextAlign.Justify
        )
        Box(modifier = Modifier
            .padding(end = 16.dp)
            .fillMaxWidth()) {
            Button(
                onClick = { navigateToListMateri(data.modulId,namaModul,desc) },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(100.dp)
            ) {
                Text(text = "Lanjut")
            }
        }

    }
    Spacer(modifier = Modifier.height(8.dp))
}