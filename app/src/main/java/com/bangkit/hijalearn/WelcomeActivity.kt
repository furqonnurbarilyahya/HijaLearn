package com.bangkit.hijalearn

import android.content.Intent
import android.os.Bundle
import android.transition.Fade
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bangkit.hijalearn.data.local.database.ModuleDatabase
import com.bangkit.hijalearn.di.Injection
import com.bangkit.hijalearn.model.User
import com.bangkit.hijalearn.navigation.Screen
import com.bangkit.hijalearn.ui.screen.login.LoginScreen
import com.bangkit.hijalearn.ui.screen.register.RegisterScreen
import com.bangkit.hijalearn.ui.screen.welcome.WelcomeScreen
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class WelcomeActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels {
        WelcomeViewModelFactory(Injection.provideWelcomeRepository(this))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val applicationScope = CoroutineScope(SupervisorJob())

        // Prepopulate Database
        val moduleDao = ModuleDatabase.getDatabase(this,applicationScope).moduleDao()
//        applicationScope.launch {
//            moduleDao.getAllModul()
//        }

        val splashScreen = installSplashScreen()
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

    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(
            Screen.Welcome.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            }
        ) {
            WelcomeScreen(
                onClickLogin = {
                    navController.navigate(Screen.Login.route)
                },
                onClickRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        composable(
            Screen.Login.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            }
        ) {
            LoginScreen(
                onClickRegister = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Welcome.route)
                    }
                },
                moveActivity = moveActivity,
                context = context
            )

        }
        composable(
            Screen.Register.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(500)
                )
            }
        ) {
            RegisterScreen(
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