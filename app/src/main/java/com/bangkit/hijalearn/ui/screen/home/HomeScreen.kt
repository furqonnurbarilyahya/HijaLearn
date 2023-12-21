package com.bangkit.hijalearn.ui.screen.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Modul
import com.bangkit.hijalearn.data.pref.UserPreference
import com.bangkit.hijalearn.data.pref.dataStore
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.data.remote.response.ModuleItem
import com.bangkit.hijalearn.data.remote.response.ProgressResponse
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.User
import com.bangkit.hijalearn.ui.component.ModulItem
import com.bangkit.hijalearn.ui.component.SectionText
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun HomeScreen (
    context: Context,
    navigateToIntroduction: (Int,String,String) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {
    val user = Firebase.auth.currentUser
    val namaModulState = remember {
        mutableStateOf("")
    }
    val descModulState = remember {
        mutableStateOf("")
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        Card (
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp)
        ) {
            Box(modifier = Modifier){
                Image(
                    painter = painterResource(id = R.drawable.bg_home),
                    contentDescription = null,
                )
                Column (
                    modifier = Modifier.padding(start = 16.dp, top = 18.dp, end = 16.dp)
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Image(
                            modifier = Modifier
                                .height(45.dp)
                                .width(45.dp),
                            painter = painterResource(R.drawable.icon_category_espresso),
                            contentDescription = null,
                        )
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Notifications,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                    
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Assalamu'alaykum " + user?.displayName
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Ayo mulai perjalanan Belajarmu",
                    fontWeight = SemiBold,
                    fontSize = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        SectionText(title = "Materi yang harus kamu pelajari!")
        Spacer(modifier = Modifier.height(10.dp))
        // Setup Card Progress
        viewModel.progessState.collectAsState(initial = UiState.Loading).value.let { it ->
            when(it){
                is UiState.Loading -> {
                    Card (
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    viewModel.getProgress()
                }
                is UiState.Success -> {
                    val data = it.data
                    val module = data.module
                    val progress = module.find { it.moduleId == data.lastModule }
                    CardProgressContent(data = data, progress = progress!!, namaModulState = namaModulState, descModulState = descModulState)
                }
                is UiState.Error -> {
                    Card (
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .fillMaxWidth()
                            .height(200.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "Gagal memuat")
                                Button(onClick = { viewModel.getProgress() }) {
                                    Text(text = "Coba lagi")
                                }
                            }
                        }
                        Toast.makeText(context,"Terjadi Kesalahan",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            SectionText(title = "Daftar Modul")
            TextButton(onClick = {}) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.view_all),
                    fontSize = 14.sp,
                    fontWeight = SemiBold
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        viewModel.allModulState.collectAsState(initial = UiState.Loading).value.let {
            when(it) {
                is UiState.Loading -> {
                    viewModel.getAllModul()
                }
                is UiState.Success -> {
                    namaModulState.value = it.data.first().namaModul
                    descModulState.value = it.data.first().deskripsi
                    ListModul(
                        context = context,
                        listModule = it.data,
                        navigateToIntroduction = navigateToIntroduction)
                }
                is UiState.Error -> {
                    Box(modifier = Modifier.height(200.dp).fillMaxWidth()) {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Gagal memuat")
                            Button(onClick = { viewModel.getProgress() }) {
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
fun ListModul(
    context: Context,
    listModule: List<Modul>,
    navigateToIntroduction: (Int, String, String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
    ){
        items(listModule ?: emptyList(), key = { it.namaModul }) { module ->
            ModulItem(
                module,
                context,
                modifier = Modifier.clickable {
                    navigateToIntroduction(module.modulId,module.namaModul,module.deskripsi)
                }
            )
        }
    }
    Spacer(modifier = Modifier.height(5.dp))
}

@Composable
fun CardProgressContent(
    data: ProgressResponse,
    progress: ModuleItem,
    namaModulState: MutableState<String>,
    descModulState: MutableState<String>
) {
    Card (
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        val percent = ((progress.subModuleDone.toDouble() / progress.totalSubModule.toDouble()) * 100).toInt()
        val namaModul = namaModulState.value
        val descModul = descModulState.value
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.6f)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Modul ${data.lastModule}",
                    fontWeight = Bold,
                    fontSize = 24.sp
                )
                Text(
                    text = "Belajar $namaModul",
                    fontWeight = SemiBold,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = descModul,
                    fontSize = 16.sp
                )
            }
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .weight(0.4f)
            ) {
                Canvas(modifier = Modifier.size(100.dp), onDraw = {
                    drawCircle(color =
                    if (percent in 0..33) {
                        Color.Red
                    } else if (percent in 34..66) {
                        Color.Yellow
                    } else {
                        Color.Green
                    }

                    )
                })
                Text(
                    text = "$percent%",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }

        }
    }
}