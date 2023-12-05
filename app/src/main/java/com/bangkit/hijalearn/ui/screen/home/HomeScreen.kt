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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.ViewModelFactory
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.Module
import com.bangkit.hijalearn.model.User
import com.bangkit.hijalearn.model.dummyModule
import com.bangkit.hijalearn.ui.component.ModulItem
import com.bangkit.hijalearn.ui.component.SectionText
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

@Composable
fun HomeScreen (
    context: Context,
    navigateToIntroduction: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideWelcomeRepository(context),Injection.provideMainRepository(context))
    )
) {
    val listModule by viewModel.listModule.collectAsState()
    val user = viewModel.getSession().collectAsState(initial = User("","","","",false))
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        Card (
            modifier = Modifier
                .height(180.dp),
            shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Column {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .height(45.dp)
                            .width(45.dp),
                        painter = painterResource(R.drawable.icon_category_espresso),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(290.dp))
                    IconButton(
                        onClick = { /*TODO*/ },
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = "Assalamu'alaykum " + user.value.username
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
                        text = "Modul 1",
                        fontWeight = Bold,
                        fontSize = 24.sp
                    )
                    Text(
                        text = "Belajar Huruf Hijaiyah",
                        fontWeight = SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah",
                        fontSize = 16.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .wrapContentSize()
                        .weight(0.4f)
                ) {
                    Canvas(modifier = Modifier.size(100.dp), onDraw = {
                        drawCircle(color = Color.Green)
                    })
                    Text(
                        text = "90%",
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row (modifier = Modifier.fillMaxWidth()){
            SectionText(title = "Daftar Modul")
            Spacer(modifier = Modifier.width(100.dp))
            Text(
                modifier = Modifier.padding(top = 17.dp),
                text = stringResource(R.string.view_all),
                fontSize = 14.sp,
                fontWeight = SemiBold
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
        ){
            items(listModule, key = { it.title }) { module ->
                ModulItem(
                    module,
                    modifier = Modifier.clickable {
                        navigateToIntroduction(module.id)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
fun HomeScreenPreview(

) {
    HijaLearnTheme {
        HomeScreen(LocalContext.current, navigateToIntroduction = {})
    }
}