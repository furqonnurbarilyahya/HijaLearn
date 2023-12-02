package com.bangkit.hijalearn

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.User
import com.bangkit.hijalearn.navigation.Screen
import com.bangkit.hijalearn.ui.screen.login.LoginScreen
import com.bangkit.hijalearn.ui.screen.login.LoginState
import com.bangkit.hijalearn.ui.screen.register.RegisterScreen
import com.bangkit.hijalearn.ui.screen.register.RegisterState
import com.bangkit.hijalearn.ui.screen.welcome.WelcomeScreen
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

class WelcomeActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels {
        ViewModelFactory(Injection.provideWelcomeRepository(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { splashViewModel.isLoading.value }
        setContent {
            HijaLearnTheme {
                val user = splashViewModel.getSession().collectAsState(initial = User("","","","",false))
                if (user.value.isLogin) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WelcomeApp(moveActivity = {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    })
                }
            }
        }
    }
}

@Composable
fun WelcomeApp(
    modifier: Modifier = Modifier,
    moveActivity: () -> Unit,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val loginState = remember { LoginState() }
    val registerState = remember { RegisterState() }
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onClickLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onClickRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(Screen.Login.route) {
            loginState.apply {
                LoginScreen(
                    email = email,
                    password = password,
                    isPasswordVisible = isPasswordVisible,
                    onValueEmailChange = {
                        onValueEmailChange(it)
                    },
                    onValuePasswordChange = {
                        onValuePasswordChange(it)
                    },
                    onClickTrailingIcon = { onClickTrailingIcon() },
                    onClickRegister = {
                        navController.navigate(Screen.Register.route) {
                            popUpTo(Screen.Welcome.route)
                        }
                    },
                    moveActivity = moveActivity,
                    context = context
                )
            }
        }
        composable(Screen.Register.route) {
            registerState.apply {
                RegisterScreen(
                    username = username,
                    email = email,
                    password = password,
                    isPasswordVisible = isPasswordVisible,
                    onValueUsernameChange = { onValueUsernameChange(it) },
                    onValueEmailChange = { onValueEmailChange(it) },
                    onValuePasswordChange = { onValuePasswordChange(it) },
                    onClickTrailingIcon = { onClickTrailingIcon() },
                    resetState = {
                        username = ""
                        email = ""
                        password = ""
                    },
                    onClickLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Welcome.route)
                        }
                    },
                    context = context
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HijaLearnTheme {
//        Greeting("Android")
    }
}