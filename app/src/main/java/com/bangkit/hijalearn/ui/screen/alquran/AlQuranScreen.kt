package com.bangkit.hijalearn.ui.screen.alquran

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.ListSurahResponseItem

@Composable
fun AlQuranScreen(
    context: Context,
    navigateToSurah: (String) -> Unit,
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
                    surahItem = surah,
                    navigateToSurah = navigateToSurah
                )
            }

            else -> {}
        }
    }
}

@Composable
fun ListSurahItem(
    surahItem: List<ListSurahResponseItem>,
    navigateToSurah: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(surahItem) {
            Card (
                modifier = Modifier
                    .clickable {
                        navigateToSurah(it.nomor)
                    }
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column (modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .width(40.dp)
                                .height(40.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                        ) {
                            Text(
                                text = it.nomor,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row {
                                Text(
                                    text = it.nama,
                                    fontSize = 16.sp,
                                    fontStyle = FontStyle.Italic
                                )
                                Text(
                                    text = " (" + it.ayat + " ayat)",
                                    fontSize = 14.sp,
                                )
                            }
                            Text(
                                text = it.arti,
                                fontSize = 12.sp
                            )
                        }
                        Row ( modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = it.asma,
                                fontSize = 26.sp
                            )
                        }

                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}