package com.bangkit.hijalearn.ui.screen.surah

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.SurahResponseItem

@Composable
fun SurahScreen(
    context: Context,
    surahId: String,
    surahName: String,
    ayat: Int,
    onClickBack: () -> Unit,
    surahViewModel: SurahViewModel = viewModel(factory = MainViewModelFactory(Injection.provideMainRepository(context))),
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
           IconButton(
               modifier = Modifier.align(CenterStart),
               onClick = onClickBack
           ) {
               Icon(
                   imageVector = Icons.Filled.ArrowBack,
                   contentDescription = null,
                   tint = Color.White
               )
           }
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = "$surahName - $ayat ayat",
                color = Color.White,
                fontSize = 18.sp
            )
        }
        surahViewModel.listAyatState.collectAsState(initial = UiState.Loading).value.let {
            when (it) {
                is UiState.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    surahViewModel.getSurahById(surahId)
                }
                is UiState.Success -> {
                    val ayat = it.data
                    ListAyatItem(ayat = ayat)
                }

                else -> {}
            }
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
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column {
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
                    Spacer(modifier = Modifier.height(5.dp))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = it.ar,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Right,
                            lineHeight = 40.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colorResource(id = R.color.light_blue)),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            text = "Artinya: " + it.id,
                            color = Color.Black,
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}