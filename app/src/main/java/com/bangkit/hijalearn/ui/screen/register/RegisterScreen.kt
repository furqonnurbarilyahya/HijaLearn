package com.bangkit.hijalearn.ui.screen.register

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme
import com.bangkit.hijalearn.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    username: String,
    email: String,
    password: String,
    isPasswordVisible: Boolean,
    onValueUsernameChange: (String) -> Unit,
    onValueEmailChange: (String) -> Unit,
    onValuePasswordChange: (String) -> Unit,
    onClickTrailingIcon: () -> Unit,
    onClickRegister: () -> Unit,
    onClickLogin: () -> Unit,
    modifier: Modifier = Modifier

) {
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(R.drawable.logo) ,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.register),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            value = username,
            onValueChange = onValueUsernameChange,
            label = { Text(text = stringResource(R.string.username))},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            value = email,
            onValueChange = onValueEmailChange,
            label = { Text(text = stringResource(R.string.email))},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    tint = Color.Black,
                    contentDescription = null
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            value = password,
            onValueChange = onValuePasswordChange,
            label = { Text(text = stringResource(R.string.password))},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    tint = Color.Black,
                    contentDescription = null
                )
            },
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (isPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                IconButton(onClick = onClickTrailingIcon) {
                    Icon(imageVector = icon, contentDescription = null,)
                }
            },
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = onClickRegister,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(stringResource(R.string.register))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.already_have_account))
            TextButton(
                onClick = onClickLogin
            ) {
                Text(
                    text = stringResource(R.string.login)
                )
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = Devices.PIXEL_3A)
@Composable
fun RegisterScreenPreview() {
    HijaLearnTheme {
        RegisterScreen(
            username = "",
            email = "",
            password = "",
            onClickLogin = {},
            onValueUsernameChange = {},
            onValueEmailChange = {},
            onValuePasswordChange = {},
            isPasswordVisible = false,
            onClickTrailingIcon = {},
            onClickRegister = {}
        )
    }
}