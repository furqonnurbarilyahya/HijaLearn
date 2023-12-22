package com.bangkit.hijalearn.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.bangkit.hijalearn.R

@Composable
fun MateriItem(
    namaModul: String,
    materi: com.bangkit.hijalearn.data.local.database.Materi,
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
                    text = "$namaModul ke-" + materi.nomor.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Siswa belajar " + materi.namaMateri,
                )
            }
            AsyncImage(
                model = if (accesable) materi.namaGambar else R.drawable.lock_icon,
                contentDescription = null,
                modifier = Modifier
                    .size(55.dp)
            )
        }
    }
}
