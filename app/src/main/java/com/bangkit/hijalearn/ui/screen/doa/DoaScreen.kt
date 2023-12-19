package com.bangkit.hijalearn.ui.screen.doa

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.bangkit.hijalearn.model.DoaResponseItem

@Composable
fun DoaScreen(
    context: Context,
    doaViewModel: DoaViewModel = viewModel(factory = MainViewModelFactory(Injection.provideMainRepository(context)))
) {
    doaViewModel.listDoaState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Center,
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                doaViewModel.getDoa()
            }

            is UiState.Success -> {
                val doa = it.data
                ListDoaItem(doaItem = doa)
            }

            else -> {}
        }
    }
}

@Composable
fun ListDoaItem(
    doaItem: List<DoaResponseItem>
) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ){
        items(doaItem) {
            Card {
                Text(text = it.doa)
            }
        }
    }
}