package com.bangkit.hijalearn.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

@Composable
fun MateriDialog(
    isLoading: Boolean,
    isCorrect: Boolean,
    isIncorrect: Boolean,
    isError: Boolean
) {
    val iconImage = if (isCorrect) {
        Icons.Filled.CheckCircle
    } else if (isError){
        Icons.Filled.Error
    } else {
        Icons.Filled.Clear
    }

    val colorHeader = if (isCorrect) {
        Color.Green
    } else if (isError){
        Color.Red
    } else if (isIncorrect) {
        Color.Red
    } else {
        MaterialTheme.colorScheme.primary
    }

    val headerMessage = if (isCorrect) {
        "Jawaban Benar"
    } else if (isError){
        "Gagal"
    } else if (isIncorrect) {
        "Jawaban Salah"
    } else {
        "Memuat"
    }

    val bodyMessage = if (isCorrect) {
        "Pengucapanmu sudah benar"
    } else if (isError){
        "Terjadi kesalahan"
    } else if (isIncorrect) {
        "Pengucapanmu masih salah"
    } else {
        "Mengevaluasi jawaban..."
    }

    Dialog(onDismissRequest = { /*TODO*/ }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            shadowElevation = 10.dp
        ) {

            Column(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .background(
                                colorHeader
                            )
                            .fillMaxWidth()
                            .padding(vertical = 32.dp)
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .size(50.dp)
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .background(
                                colorHeader
                            )
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Image(
                            imageVector = iconImage,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(100.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = headerMessage,
                        fontSize = 24.sp,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = bodyMessage)
                    Spacer(modifier = Modifier.height(16.dp))
                    if (!isLoading) {
                        Button(
                            onClick = { /*TODO*/ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = "Okay")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MateriDialogPreview() {
    HijaLearnTheme {
        MateriDialog(
            isLoading = false,
            isCorrect = true,
            isIncorrect = false,
            isError = false
        )
    }
}