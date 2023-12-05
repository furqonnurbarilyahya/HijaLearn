package com.bangkit.hijalearn.ui.screen.introduction

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.ViewModelFactory
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

@Composable
fun IntroductionScreen(
    context: Context,
    id: Int,
    onClickBack: () -> Unit,
    navigateToListMateri: (Int) -> Unit,
    viewModel: IntroductionViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideWelcomeRepository(context), Injection.provideMainRepository(context))
    )
) {
    val introduction = viewModel.getIntroductionById(id)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.End
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            IconButton(
                onClick =  onClickBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }
            Text(
                text = introduction.judul,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp, start = 16.dp, end = 16.dp)
                .weight(0.9f)
        ) {
            Text(
                text = introduction.judul1,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = introduction.desc1)

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = introduction.judul2,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = introduction.desc2
            )
            Box(modifier = Modifier
                .padding(end = 16.dp)
                .fillMaxWidth()) {
                Button(
                    onClick = { navigateToListMateri(introduction.id) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .width(100.dp)
                ) {
                    Text(text = "Lanjut")
                }
            }

        }




        Spacer(modifier = Modifier.height(8.dp))
    }
}

//@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_4)
//@Composable
//fun IntroductionScreenPreview() {
//    HijaLearnTheme {
//        IntroductionScreen(onClickBack = {})
//    }
//}