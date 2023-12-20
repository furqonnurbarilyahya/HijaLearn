package com.bangkit.hijalearn.ui.screen.alquran

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Notifications
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.ListSurahResponseItem
import com.bangkit.hijalearn.ui.component.SearchBar

@Composable
fun AlQuranScreen(
    context: Context,
    navigateToSurah: (String, String, Int) -> Unit,
    alQuranViewModel: AlQuranViewModel =  viewModel(factory = MainViewModelFactory(Injection.provideMainRepository(context))),
) {

    var searchSurah by remember { mutableStateOf("") }
    var listSurah by remember { mutableStateOf(emptyList<ListSurahResponseItem>()) }

    alQuranViewModel.listSurahState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
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
                Column (modifier = Modifier.fillMaxSize()){
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.bg_home),
                            contentDescription = null
                        )
                        Column (
                            modifier = Modifier.padding(top = 30.dp),
                            horizontalAlignment = CenterHorizontally
                        ){
                            Text(
                                text = "القرآن الكريم",
                                color = Color.White,
                                fontSize = 50.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)
                                    .padding(horizontal = 40.dp)
                                    .fillMaxWidth()
                                    .background(color = Color.White)
                            )
                            Text(
                                text = "Al-Qur'an Nul Karim",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Italic
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            SearchBar(
                                onSearchTextChanged = {
                                    searchSurah = it
                                },
                                placeHolder = "Cari Surah"
                            )
                        }
                    }
                    ListSurahItem(
                        surahItem = surah,
                        navigateToSurah = navigateToSurah,
                        searchQuery = searchSurah
                    )
                }
            }

            else -> {}
        }
    }
}

@Composable
fun ListSurahItem(
    surahItem: List<ListSurahResponseItem>,
    navigateToSurah: (String, String, Int) -> Unit,
    searchQuery: String
) {
    val filteredSurah = surahItem.filter {
        it.nama.contains(searchQuery, ignoreCase = true)
    }
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ) {
        items(filteredSurah) {
            Card (
                modifier = Modifier
                    .clickable {
                        navigateToSurah(it.nomor, it.nama, it.ayat)
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
                                .width(40.dp)
                                .height(40.dp)
                                .clip(shape = RoundedCornerShape(10.dp))
                        ) {
                            Box {
                                Image(
                                    painter = painterResource(id = R.drawable.iconayat),
                                    contentDescription = null
                                )
                                Text(
                                    modifier = Modifier.align(Alignment.Center),
                                    text = it.nomor,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row {
                                Text(
                                    text = it.nama,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
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