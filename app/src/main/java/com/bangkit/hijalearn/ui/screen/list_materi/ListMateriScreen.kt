package com.bangkit.hijalearn.ui.screen.list_materi

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.WelcomeViewModelFactory
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.ui.component.MateriItem

@Composable
fun ListMateriScreen(
    context: Context,
    modulId: Int,
    namaModul: String,
    desc: String,
    onClickBack: () -> Unit,
    navigateToMateri: (Int,Int,String) -> Unit,
    viewModel: ListMateriViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {

    val currentProgress = remember {
        viewModel.countMateriSelesaiByModul(modulId)
    }

    val totalCompleted by viewModel.totalCompleted.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
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
                text = "Modul $modulId | $namaModul",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        // List Materi
        viewModel.listMateriState.collectAsState(initial = UiState.Loading).value.let {
            when (it) {
                is UiState.Loading -> {
                    viewModel.getAllMateriWithModulByModulId(modulId)
                }
                is UiState.Success -> {
                    val data = it.data
                    Spacer(modifier = Modifier.height(26.dp))
                    Text(
                        text = namaModul,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = desc,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(items = data, key = { it.id }) { materi ->
                            MateriItem(
                                materi = materi,
                                accesable = materi.nomor <= (totalCompleted ?: 0),
                                modifier = Modifier.clickable {
                                    if (materi.nomor <= (totalCompleted?:0)) {
                                        navigateToMateri(materi.nomor, modulId, namaModul)
                                    }
//                                    navigateToMateri(materi.nomor,modulId,namaModul)
                                }
                            )
                        }
                    }
                }
                is UiState.Error -> {
                    // Do nothing
                }
            }
        }
    }
}


//@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
//@Composable
//fun IntroductionScreenPreview() {
//    HijaLearnTheme {
//        ListMateriScreen(1,onClickBack = {})
//    }
//}