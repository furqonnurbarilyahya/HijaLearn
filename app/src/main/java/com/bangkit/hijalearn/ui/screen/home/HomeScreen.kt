package com.bangkit.hijalearn.ui.screen.home

import android.content.Context
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
import com.bangkit.hijalearn.data.remote.repository.MainRepository
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.User
import com.bangkit.hijalearn.ui.component.ModulItem
import com.bangkit.hijalearn.ui.component.SectionText

@Composable
fun HomeScreen (
    context: Context,
    navigateToIntroduction: (Int,String,String) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {
    val user = viewModel.getSession().collectAsState(initial = User("","","","",false))
    val namaModulState = remember {
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Assalamu'alaykum " + user.value.username,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Apakah sudah siap untuk belajar?",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        SectionText(title = "Materi yang harus kamu pelajari!")
        Spacer(modifier = Modifier.height(10.dp))
        // Setup Card Progress
        viewModel.progessState.collectAsState(initial = UiState.Loading).value.let {
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
                    val progress = data.modulProgress.find { it.modulId == data.lastModulAccesId }
                    CardProgressContent(data = data, progress = progress, namaModulState = namaModulState)
                }
                is UiState.Error -> {

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
                    ListModul(
                        context = context,
                        listModule = it.data,
                        navigateToIntroduction = navigateToIntroduction)
                }
                is UiState.Error -> {

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
    data: MainRepository.TesProgressResponse,
    progress: MainRepository.TesModulProgress?,
    namaModulState: MutableState<String>,
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
        if (progress == null) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = "Kamu belum ambil kelas", modifier = Modifier.align(
                    Alignment.Center))
            }
        } else {
            val percent = ((progress.totalCompletedSubmodul.toDouble() / progress.totalSubmodul.toDouble())*100).toInt()
            val namaModul = namaModulState.value
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
                        text = "Modul ${data.lastModulAccesId}",
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
                        text = "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah/Dummy",
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
                        if (percent >= 0 && percent <= 33) {
                            Color.Red
                        } else if (percent >= 34 && percent <= 66) {
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
}