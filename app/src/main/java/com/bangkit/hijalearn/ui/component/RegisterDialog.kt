package com.bangkit.hijalearn.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

@Composable
fun RegisterDialog() {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Daftar Berhasil",
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp,
                )
                Spacer(modifier = Modifier.height(32.dp))
                Image(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp),
                    colorFilter = ColorFilter.tint(Color.Green)
                )
                Text(text = "Mengarahkan ke layar masuk")
            }
        }
    }
}

@Preview
@Composable
fun RegisterDialogPreview() {
    HijaLearnTheme {
        RegisterDialog()
    }
}