package com.bangkit.hijalearn.ui.screen.register

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.WelcomeViewModelFactory
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.ui.component.RegisterDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onClickLogin: () -> Unit,
    context: Context,
    viewModel: RegisterViewModel = viewModel(
        factory = WelcomeViewModelFactory(Injection.provideWelcomeRepository(context))
    ),
    modifier: Modifier = Modifier
) {
    val registerResult by viewModel.registerResult.collectAsState()

    if (viewModel.showDialog) {
        RegisterDialog()
    }

    when (val state = registerResult) {
        is Result.Success -> {
            viewModel.resetLoading()
            viewModel.showSuccessDialog(onClickLogin)
        }
        is Result.Error -> {
            state.error.getContentIfNotHandled()?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
            viewModel.resetLoading()
        }
        is Result.Loading -> {
            viewModel.isLoading = state.isLoading
        }
    }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.welcome_background),
                contentScale = ContentScale.FillBounds
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(95.dp))
        Image(
            painter = painterResource(R.drawable.reglog) ,
            contentDescription = "Logo",
            modifier = Modifier
                .width(220.dp)
                .height(220.dp)
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
            value = viewModel.username,
            onValueChange = viewModel::onValueUsernameChange,
            label = { Text(text = stringResource(R.string.username))},
            isError = viewModel.isUsernameEmpty,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    tint = Color.Black,
                    contentDescription = null
                )
            },
            supportingText = {
                if (viewModel.isUsernameEmpty) {
                    Text(text = "Username tidak boleh kosong", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            value = viewModel.email,
            onValueChange = viewModel::onValueEmailChange,
            label = { Text(text = stringResource(R.string.email))},
            isError = viewModel.isEmailEmpty || viewModel.isEmailNotValid,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    tint = Color.Black,
                    contentDescription = null
                )
            },
            supportingText = {
                if (viewModel.isEmailEmpty) {
                    Text(text = "Email tidak boleh kosong", color = Color.Red)
                } else if (viewModel.isEmailNotValid) {
                    Text(text = "Email tidak valid", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            value = viewModel.password,
            onValueChange = viewModel::onValuePasswordChange,
            label = { Text(text = stringResource(R.string.password))},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    tint = Color.Black,
                    contentDescription = null
                )
            },
            singleLine = true,
            visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            isError = viewModel.isPasswordEmpty || viewModel.isPasswordNotValid,
            trailingIcon = {
                val icon = if (viewModel.isPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                IconButton(onClick = viewModel::onClickTrailingIcon) {
                    Icon(imageVector = icon, contentDescription = null,)
                }
            },
            supportingText = {
                if (viewModel.isPasswordEmpty) {
                    Text(text = "Password tidak boleh kosong", color = Color.Red)
                } else if (viewModel.isPasswordNotValid) {
                    Text(text = "Panjang password harus lebih dari 6", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                      viewModel.register()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            enabled = !viewModel.isLoading,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp
            )
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(end = 8.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = if (viewModel.isLoading) "Sedang daftar.." else "Register"
                )
            }
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
            onClickLogin = {},
            context = LocalContext.current
        )
    }
}