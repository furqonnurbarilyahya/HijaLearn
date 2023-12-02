package com.bangkit.hijalearn.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.model.Module
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme


@Composable
fun ModuleRow(
    listModule: List<Module>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ){
        items(listModule, key = { it.title }) { module ->
            ModulItem(module)
        }
    }
}
@Composable
fun ModulItem(
    module: Module,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.width(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column {
            Image(
                painter = painterResource(module.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column (modifier = Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                )
                Text(
                    text = module.description,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ModulItemPreview() {
    HijaLearnTheme {
        ModulItem(
            module = Module(R.drawable.banner, "Modul 1 | Huruf Hijaiyah", "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah.")
        )
    }
}