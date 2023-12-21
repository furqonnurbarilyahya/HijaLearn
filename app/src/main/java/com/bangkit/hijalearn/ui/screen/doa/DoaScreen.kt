package com.bangkit.hijalearn.ui.screen.doa

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.DoaResponseItem
import com.bangkit.hijalearn.ui.component.SearchBar

@Composable
fun DoaScreen(
    context: Context,
    doaViewModel: DoaViewModel = viewModel(factory = MainViewModelFactory(Injection.provideMainRepository(context)))
) {
    var searchDoa by remember { mutableStateOf("") }

    doaViewModel.listDoaState.collectAsState(initial = UiState.Loading).value.let {
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
                doaViewModel.getDoa()
            }

            is UiState.Success -> {
                val doa = it.data
                Column(modifier = Modifier.fillMaxSize()) {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.bg_home),
                            contentDescription = null
                        )
                        Column(
                            modifier = Modifier.padding(top = 55.dp),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            Text(
                                text = "Doa Harian Muslim",
                                color = Color.White,
                                fontSize = 30.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(
                                modifier = Modifier
                                    .height(2.dp)
                                    .padding(horizontal = 40.dp)
                                    .fillMaxWidth()
                                    .background(color = Color.White)
                            )
                            Spacer(modifier = Modifier.height(37.dp))
                            SearchBar(
                                onSearchTextChanged = {
                                    searchDoa = it
                                },
                                placeHolder = "Cari Doa"
                            )
                        }
                    }
                    ListDoa(
                        doaItem = doa,
                        searchQuery = searchDoa
                    )
                }

            }
            else -> {
                Toast.makeText(context, "Maaf, tidak ada koneksi internet", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun ListDoaItem(
    doa: String,
    latin: String,
    ayat: String,
    arti: String
) {
    var isExpanded by remember { mutableStateOf(false) }
    val animatedSize by animateDpAsState(
        targetValue = if (isExpanded) 120.dp else 45.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Column (
            modifier = Modifier
                .padding(12.dp)
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = doa,
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.headlineLarge
                )
                IconButton(
                    onClick = { isExpanded = !isExpanded }
                ) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (isExpanded) "Show less" else "Show more"
                    )
                }
            }
            if (isExpanded) {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = ayat,
                        fontSize = 24.sp,
                        lineHeight = 32.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Column (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = latin,
                            fontSize = 15.sp,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.W500,
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "Artinya: $arti",
                            fontSize = 15.sp,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ListDoa(
    doaItem: List<DoaResponseItem>,
    searchQuery: String
) {
    val filteredDoa = doaItem.filter {
        it.doa.contains(searchQuery, ignoreCase = true)
    }
    LazyColumn(
        modifier = Modifier.padding(10.dp)
    ){
        items(filteredDoa) {
            ListDoaItem(
                doa = it.doa,
                latin = it.latin,
                ayat = it.ayat,
                arti = it.artinya
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}