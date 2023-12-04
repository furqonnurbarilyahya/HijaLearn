package com.bangkit.hijalearn.ui.screen.login

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bangkit.hijalearn.MainActivity
import com.bangkit.hijalearn.R
import com.bangkit.hijalearn.ViewModelFactory
import com.bangkit.hijalearn.data.Result
import com.bangkit.hijalearn.di.Injection
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onClickRegister: () -> Unit,
    moveActivity: () -> Unit,
    context: Context,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideWelcomeRepository(context),Injection.provideMainRepository(context))
    ),
    modifier: Modifier = Modifier
) {
    val loginResult by viewModel.result.collectAsState()
    when (val state = loginResult) {
        is Result.Success -> {
            viewModel.saveSession(state.data)
            viewModel.resetLoading()
            moveActivity()
        }
        is Result.Error -> {
            state.error.getContentIfNotHandled()?.let {
                if (it == "Invalid User") {
                    viewModel.loginInvalidUser = true
                }
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
            viewModel.resetLoading()
        }
        is Result.Loading -> {
            viewModel.isLoading = state.isLoading
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.welcome_background),
                contentScale = ContentScale.FillBounds
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .width(220.dp)
                .height(186.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Masuk",
            fontWeight = FontWeight.SemiBold,
            fontSize = 26.sp,
        )
        Spacer(modifier = Modifier.height(15.dp))
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = viewModel::onValueEmailChange,
            label = { Text(text = stringResource(R.string.email)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null
                )
            },
            singleLine = true,
            isError = viewModel.isEmailEmpty || viewModel.isEmailNotValid || viewModel.loginInvalidUser,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            supportingText = {
                if (viewModel.isEmailEmpty) {
                    Text(text = "Email tidak boleh kosong", color = Color.Red)
                } else if (viewModel.isEmailNotValid){
                    Text(text = "Email tidak valid", color = Color.Red)
                } else if (viewModel.loginInvalidUser) {
                    Text(text = "Email atau Password salah", color = Color.Red)
                }
            }
        )
        Spacer(modifier = Modifier.padding(bottom = 20.dp))
        OutlinedTextField(
            value = viewModel.password,
            onValueChange = viewModel::onValuePasswordChange,
            label = { Text(text = stringResource(R.string.password)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null
                )
            },
            singleLine = true,
            isError = viewModel.isPasswordEmpty || viewModel.loginInvalidUser,
            visualTransformation = if (viewModel.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            trailingIcon = {
                val icon = if (viewModel.isPasswordVisible) Icons.Outlined.VisibilityOff else Icons.Outlined.Visibility
                IconButton(onClick = viewModel::onClickTrailingIcon) {
                    Icon(imageVector = icon, contentDescription = null,)
                }
            },
            supportingText = {
                if (viewModel.isPasswordEmpty) {
                    Text(text = "Password tidak boleh kosong", color = Color.Red)
                } else if (viewModel.loginInvalidUser) {
                    Text(text = "Email atau Password salah", color = Color.Red)
                }
            },
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
        ) {
            TextButton(
                onClick = onClickRegister,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(text = stringResource(R.string.forgot_password))
            }
        }
        Spacer(modifier = Modifier.padding(bottom = 15.dp))
        Button(
            onClick = {
                    checkInputValid(viewModel)
                    viewModel.loginInvalidUser = false
                    if (viewModel.isEmailEmpty || viewModel.isEmailNotValid || viewModel.isPasswordEmpty) {
                        // Do nothing
                        viewModel.resetLoading()
                    } else {
                        viewModel.login(viewModel.email, viewModel.password)
                    }

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
                    text = if (viewModel.isLoading) "Sedang masuk.." else "Masuk"
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Belum punya akun?")
            TextButton(
                onClick = onClickRegister
            ) {
                Text(
                    text = stringResource(R.string.register)
                )
            }
        }
    }
}

fun checkInputValid(viewModel: LoginViewModel) {
    viewModel.isEmailEmpty = viewModel.email == ""
    viewModel.isPasswordEmpty = viewModel.password == ""
    viewModel.isEmailNotValid = isEmailNotValid(viewModel.email)
}
fun isEmailNotValid(email: String): Boolean {
    return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
}

@Composable
@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_3A)
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onClickRegister = {},
            moveActivity = {},
            context = LocalContext.current
        )
    }
}