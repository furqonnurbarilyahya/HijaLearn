package com.bangkit.hijalearn.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.hijalearn.R
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.bangkit.hijalearn.navigation.Screen
import com.bangkit.hijalearn.ui.component.LogoutDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun ProfileScreen(
    navController: NavController,
    logout: () -> Unit
) {
    val user = Firebase.auth.currentUser

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        LogoutDialog(
            onClickYes = {
                logout()
            },
            onClickNo = {
                showDialog = false
            }
        )
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.bg_profile),
                contentDescription = null
            )
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(shape = RoundedCornerShape(30.dp)),
                        model = if (user?.photoUrl == null) "https://assets-a1.kompasiana.com/items/album/2021/03/24/blank-profile-picture-973460-1280-605aadc08ede4874e1153a12.png?t=o&v=1200" else user.photoUrl,
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row (
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Text(
                        text = user?.displayName!!,
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white2))
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
                        .clickable {
                            navController.navigate(Screen.DetailProfile.route)
                        }
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Detail Profil",
                        fontSize = 16.sp
                    )
                    Text(text = ">")
                }
                Divider(thickness = 0.6.dp, color = Color.Black)
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate(Screen.MyAccount.route)
                        }
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
                onClick = {
                          showDialog = true
                },
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