package com.bangkit.hijalearn.ui.screen.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.model.Module
import com.bangkit.hijalearn.model.dummyModule
import com.bangkit.hijalearn.ui.component.ModuleRow
import com.bangkit.hijalearn.ui.component.SectionText
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

@Composable
fun HomeScreen () {
    Column (
        modifier = Modifier
            .fillMaxSize()
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
                        .padding(top = 15.dp, start = 10.dp, end = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier,
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
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Assalamu'alaykum Username"
                )
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
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
                .height(150.dp),
//            colors = CardDefaults.cardColors(
//                containerColor = MaterialTheme.colorScheme.background
//            )
        ) {
            Column (verticalArrangement = Arrangement.Center) {
                Row (
                    modifier = Modifier
                        .height(80.dp)
                        .padding(vertical = 12.dp, horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Modul 1 | ",
                        fontSize = 18.sp,
                        fontWeight = SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = "Belajar Huruf Hijaiyah",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.width(30.dp))
                    Canvas(modifier = Modifier.size(50.dp), onDraw = {
                        drawCircle(color = Color.Red)
                    })
                }

            }
            Divider(color = Color.Black, thickness = 0.5.dp)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                text = "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah.",
                fontSize = 16.sp,
                color = Color.Black
            )
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
        ModuleRow(dummyModule)
        Spacer(modifier = Modifier.height(5.dp))
//        Card (
//            modifier = Modifier
//                .padding(horizontal = 10.dp)
//                .fillMaxWidth()
//                .height(150.dp),
//        ) {
//            Column (verticalArrangement = Arrangement.Center) {
//                Row (
//                    modifier = Modifier
//                        .height(80.dp)
//                        .padding(vertical = 12.dp, horizontal = 10.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Modul 1 | ",
//                        fontSize = 18.sp,
//                        fontWeight = SemiBold,
//                        color = Color.Black
//                    )
//                    Text(
//                        text = "Belajar Huruf Hijaiyah",
//                        fontSize = 18.sp,
//                        color = Color.Black
//                    )
//                    Spacer(modifier = Modifier.width(30.dp))
//                    Canvas(modifier = Modifier.size(50.dp), onDraw = {
//                        drawCircle(color = Color.Red)
//                    })
//                }
//
//            }
//            Divider(color = Color.Black, thickness = 0.5.dp)
//            Text(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(10.dp),
//                text = "Siswa akan belajar tentang pengenalan dan pengucapan huruf hijaiyah.",
//                color = Color.Black
//            )
//        }
    }
}
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
fun HomeScreenPreview(

) {
    HijaLearnTheme {
        HomeScreen()
    }
}