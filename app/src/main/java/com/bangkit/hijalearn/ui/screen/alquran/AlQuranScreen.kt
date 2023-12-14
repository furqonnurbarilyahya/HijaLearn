package com.bangkit.hijalearn.ui.screen.alquran

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.ListSurahResponseItem

@Composable
fun AlQuranScreen(
    context: Context,
    alQuranViewModel: AlQuranViewModel =  viewModel(factory = MainViewModelFactory(Injection.provideMainRepository(context))),
) {

    alQuranViewModel.listSurahState.collectAsState(initial = UiState.Loading).value.let {
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
                alQuranViewModel.getListSurah()
            }

            is UiState.Success -> {
                val surah = it.data
                //content
                ListSurahItem(
                    surahItem = surah
                )
            }

            else -> {}
        }
    }
}

@Composable
fun ListSurahItem(
    surahItem: List<ListSurahResponseItem>,
) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(surahItem) {
            Card (modifier = Modifier.fillMaxWidth()) {
                Text(text = it.nama)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}