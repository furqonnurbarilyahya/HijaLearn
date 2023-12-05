package com.bangkit.hijalearn.ui.screen.list_materi

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.ViewModelFactory
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.Materi
import com.bangkit.hijalearn.ui.component.MateriItem
import com.bangkit.hijalearn.ui.screen.introduction.IntroductionScreen
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ListMateriScreen(
    context: Context,
    modulId: Int,
    onClickBack: () -> Unit,
    navigateToMateri: (Int) -> Unit,
    viewModel: ListMateriViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideWelcomeRepository(context),Injection.provideMainRepository(context))
    )
) {
    val listMateri = remember {
        viewModel.getAllMateriByModulId(modulId)
    }
    val currentProgress = remember {
        viewModel.countMateriSelesaiByModul(modulId)
    }
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
                text = "Modul 1 | Belajar Huruf Hijaiyah",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(26.dp))
        Text(
            text = "Belajar Huruf Hijaiyah",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Pada modul ini siswa akan belajar tentang pengenalan dan pengucapan huruf hijaiyah.",
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(items = listMateri, key = {it.id}){materi ->
                MateriItem(
                    materi = materi,
                    accesable = materi.id <= currentProgress+1,
                    modifier = Modifier.clickable {
                            if (materi.id <= currentProgress+1) {
                                navigateToMateri(materi.id)
                            }
                        }
                )
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