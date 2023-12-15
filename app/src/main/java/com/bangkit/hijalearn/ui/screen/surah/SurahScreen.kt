package com.bangkit.hijalearn.ui.screen.surah

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.SurahResponseItem

@Composable
fun SurahScreen(
    context: Context,
    id: String,
    onClickBack: () -> Unit,
    surahViewModel: SurahViewModel = viewModel(factory = MainViewModelFactory(Injection.provideMainRepository(context))),
) {
    surahViewModel.listAyatState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                surahViewModel.getSurahById(id)
            }
            is UiState.Success -> {
                val ayat = it.data
                ListAyatItem(ayat = ayat)
            }

            else -> {}
        }
    }
}

@Composable
fun ListAyatItem(
    ayat: List<SurahResponseItem>
) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(ayat) {
            Text(
                text = it.ar,
                fontSize = 16.sp
            )
        }
    }
}