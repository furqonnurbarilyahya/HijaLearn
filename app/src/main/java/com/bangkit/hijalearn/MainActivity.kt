package com.bangkit.hijalearn

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.hijalearn.model.BottomBarItem
import com.bangkit.hijalearn.navigation.Screen
import com.bangkit.hijalearn.ui.screen.home.HomeScreen
import com.bangkit.hijalearn.ui.screen.introduction.IntroductionScreen
import com.bangkit.hijalearn.ui.screen.list_materi.ListMateriScreen
import com.bangkit.hijalearn.ui.screen.materi.MateriScreen
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("TES","Main")
        super.onCreate(savedInstanceState)
        setContent {
            HijaLearnTheme {
                HijaLearnApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HijaLearnApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Introduction.route &&
                currentRoute != Screen.ToListMateri.route &&
                currentRoute != Screen.Materi.route) {
                BottomBar()
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    LocalContext.current,
                    navigateToIntroduction = {id ->
                        navController.navigate(Screen.Introduction.createRoute(id))
                    }
                )
            }
            composable(
                route = Screen.Introduction.route,
                arguments = listOf(navArgument("id"){ type= NavType.IntType })
            ){
                val id = it.arguments?.getInt("id")
                IntroductionScreen(
                    context = context,
                    id = id!!,
                    onClickBack = {
                        navController.navigateUp()
                    },
                    navigateToListMateri = {modulId ->
                        navController.navigate(Screen.ToListMateri.createRoute(modulId))
                    }
                )
            }
            composable(
                route = Screen.ToListMateri.route,
                arguments = listOf(navArgument("modulId"){ type = NavType.IntType })
            ){
                val modulId = it.arguments?.getInt("modulId")
                ListMateriScreen(
                    context = context,
                    modulId = modulId!!,
                    onClickBack = {
                        navController.navigateUp()
                    },
                    navigateToMateri = {materiId ->
                        navController.navigate(Screen.Materi.createRoute(materiId))
                    }
                )
            }
            composable(
                route = Screen.Materi.route,
                arguments = listOf(navArgument("materiId"){ type = NavType.IntType })
            ) {
                val materiId = it.arguments?.getInt("materiId")
                MateriScreen(
                    context = context,
                    materiId = materiId!!,
                    onClickBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navigationItems = listOf(
            BottomBarItem(
                title = "Home",
                icon = Icons.Default.Home
            ),
            BottomBarItem(
                title = "Materi",
                icon = Icons.Default.Favorite
            ),
            BottomBarItem(
                title = "Al-Qur'an",
                icon = Icons.Default.AccountCircle
            ),
            BottomBarItem(
                title = "Profil",
                icon = Icons.Default.AccountCircle
            ),
        )
        navigationItems.map {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(it.title)
                },
                selected = it.title == navigationItems[0].title,
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = Devices.PIXEL_3A)
@Composable
fun JetHeroesAppPreview() {
    HijaLearnTheme {
        HijaLearnApp()
    }
}