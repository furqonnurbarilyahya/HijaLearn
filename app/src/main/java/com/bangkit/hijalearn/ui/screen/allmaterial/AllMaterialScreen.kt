package com.bangkit.hijalearn.ui.screen.allmaterial

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.hijalearn.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.data.UiState
import com.bangkit.hijalearn.data.local.database.Modul
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.ui.component.AllModulItem

@Composable
fun AllMaterialScreen(
    context: Context,
    modifier: Modifier = Modifier,
    navigateToIntroduction: (Int, String, String) -> Unit,
    viewModel: AllMaterialViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {
    val moduleName = remember {
        mutableStateOf("")
    }
    viewModel.allModuleState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAllModule()
            }

            is UiState.Success -> {
                moduleName.value = it.data.first().namaModul
                Column{
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.bg_home),
                            contentDescription = null
                        )
                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Row (
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp, bottom = 10.dp),
                            ) {
                                Text(
                                    text = "Semua Modul Pembelajaran",
                                    color = Color.White,
                                    fontSize = 27.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp).width(370.dp).background(color = Color.White))
                            Row (
                                horizontalArrangement = Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 30.dp),
                            ) {
                                Text(
                                    text = "\"Masa depan adalah milik mereka yang menyiapkan hari ini.\"",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.W400,
                                    fontStyle = FontStyle.Italic,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    ListAllModule(
                        context = context,
                        listModule = it.data,
                        navigateToIntroduction = navigateToIntroduction
                    )
                }
            }

            else -> {}
        }
    }

}

@Composable
fun ListAllModule(
    context: Context,
    listModule: List<Modul>,
    navigateToIntroduction: (Int, String, String) -> Unit
) {
    LazyColumn (modifier = Modifier.padding(10.dp)) {
        items(listModule, key = { it.namaModul }) {
            AllModulItem(
                module = it,
                context,
                modifier = Modifier.clickable {
                    navigateToIntroduction(it.modulId, it.namaModul, it.deskripsi)
                }
            )
        }
    }
}