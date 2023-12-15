package com.bangkit.hijalearn.ui.screen.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainViewModelFactory
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.User

@Composable
fun ProfileScreen(
    context: Context,
    viewModel: ProfileViewModel = viewModel(
        factory = MainViewModelFactory(Injection.provideMainRepository(context))
    )
) {
    val user = viewModel.getSession().collectAsState(initial = User("", "", "", "", false))
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.icon_category_espresso),
            contentDescription = null,
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
                .padding(vertical = 10.dp)
        )
        Text(
            text = user.value.username,
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = colorResource(id = R.color.white2),
                    shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
                )
                .clip(shape = RoundedCornerShape(20.dp))
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Informasi Pribadi",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column (
                modifier = Modifier
                    .background(color = Color.White)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Profil",
                        fontSize = 16.sp
                    )
                    Text(text = ">")
                }
                Divider(thickness = 0.6.dp, color = Color.Black)
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Akun saya",
                        fontSize = 16.sp
                    )
                    Text(text = ">")
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Lainnya",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Column (
                modifier = Modifier
                    .background(color = Color.White)
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Bantuan",
                        fontSize = 16.sp,

                        )
                    Text(text = ">")
                }
                Divider(thickness = 0.6.dp, color = Color.Black)
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tentang aplikasi",
                        fontSize = 16.sp
                    )
                    Text(text = ">")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = "Keluar",
                    fontSize = 16.sp
                )
            }
        }
    }
}