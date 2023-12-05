package com.bangkit.hijalearn.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.model.Materi
import com.bangkit.hijalearn.model.dummyMateri
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

@Composable
fun MateriItem(
    materi: Materi,
    accesable: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        colors = if (accesable) {
            CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )
        } else {
            CardDefaults.cardColors(
                containerColor = Color.LightGray,
                Color.Gray
            )
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(0.8f)
            ) {
                Text(
                    text = "Huruf Hijaiyah ke-" + materi.id.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Siswa belajar huruf " + materi.namaHuruf,
                )
            }
            Image(
                painter = if (accesable) {
                    painterResource(id = materi.gambar)
                } else {
                       painterResource(id = R.drawable.ic_lock)
                }
                ,
                contentDescription = null,
                modifier = Modifier
                    .size(55.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MateriItemPreview() {
    HijaLearnTheme {
        MateriItem(materi = dummyMateri[0], false)
    }
}