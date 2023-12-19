package com.bangkit.hijalearn

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bangkit.hijalearn.model.BottomBarItem
import com.bangkit.hijalearn.navigation.Screen
import com.bangkit.hijalearn.ui.screen.allmaterial.AllMaterialScreen
import com.bangkit.hijalearn.ui.screen.alquran.AlQuranScreen
import com.bangkit.hijalearn.ui.screen.detail_profile.DetailProfileScreen
import com.bangkit.hijalearn.ui.screen.home.HomeScreen
import com.bangkit.hijalearn.ui.screen.introduction.IntroductionScreen
import com.bangkit.hijalearn.ui.screen.list_materi.ListMateriScreen
import com.bangkit.hijalearn.ui.screen.materi.MateriScreen
import com.bangkit.hijalearn.ui.screen.my_acccount.MyAccountScreen
import com.bangkit.hijalearn.ui.screen.profile.ProfileScreen
import com.bangkit.hijalearn.ui.screen.surah.SurahScreen
import com.bangkit.hijalearn.ui.theme.HijaLearnTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
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
            if (
                currentRoute != Screen.Introduction.route &&
                currentRoute != Screen.ToListMateri.route &&
                currentRoute != Screen.Materi.route &&
                currentRoute != Screen.Surah.route &&
                currentRoute != Screen.DetailProfile.route &&
                currentRoute != Screen.MyAccount.route) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(
                Screen.Home.route,
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
            ){
                HomeScreen(
                    LocalContext.current,
                    navigateToIntroduction = {id,namaModul,desc ->
                        navController.navigate(Screen.Introduction.createRoute(
                            id,
                            Uri.decode(namaModul),
                            Uri.decode(desc)
                        )
                        )
                    }
                )
            }
            composable(
                route = Screen.Introduction.route,
                arguments = listOf(
                    navArgument("id"){ type= NavType.IntType },
                    navArgument("namaModul"){ type= NavType.StringType },
                    navArgument("desc"){ type= NavType.StringType }
                )
            ){
                val id = it.arguments?.getInt("id")
                val namaModul = Uri.decode(it.arguments?.getString("namaModul"))
                val desc = Uri.decode(it.arguments?.getString("desc"))
                IntroductionScreen(
                    context = context,
                    id = id!!,
                    namaModul = namaModul!!,
                    desc = desc!!,
                    onClickBack = {
                        navController.navigateUp()
                    },
                    navigateToListMateri = {modulId, namaMoudl, desc ->
                        navController.navigate(Screen.ToListMateri.createRoute(
                            modulId,
                            Uri.decode(namaModul),
                            Uri.decode(desc)
                        )
                        )
                    }
                )
            }
            composable(
                route = Screen.ToListMateri.route,
                arguments = listOf(
                    navArgument("modulId"){ type = NavType.IntType },
                    navArgument("namaModul"){ type= NavType.StringType },
                    navArgument("desc"){ type= NavType.StringType }
                )
            ){
                val modulId = it.arguments?.getInt("modulId")
                val namaModul = Uri.decode(it.arguments?.getString("namaModul"))
                val desc = Uri.decode(it.arguments?.getString("desc"))
                ListMateriScreen(
                    context = context,
                    modulId = modulId!!,
                    namaModul = namaModul,
                    desc = desc,
                    onClickBack = {
                        navController.navigateUp()
                    },
                    navigateToMateri = {materiId, modulId, namaModul->
                        navController.navigate(Screen.Materi.createRoute(materiId,modulId,Uri.decode(namaModul)))
                    }
                )
            }
            composable(
                route = Screen.Materi.route,
                arguments = listOf(
                    navArgument("nomor"){ type = NavType.IntType },
                    navArgument("modulId"){ type = NavType.IntType },
                    navArgument("namaModul"){ type = NavType.StringType }
                )
            ) {
                val nomor = it.arguments?.getInt("nomor")
                val modulId = it.arguments?.getInt("modulId")
                val namaModul = Uri.decode(it.arguments?.getString("namaModul"))
                MateriScreen(
                    context = context,
                    nomor = nomor!!,
                    modulId = modulId!!,
                    namaModul = namaModul!!,
                    onClickBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
            composable(Screen.DetailProfile.route) {
                DetailProfileScreen(
                    onClickBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.MyAccount.route) {
                MyAccountScreen(
                    onClickBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.AlQuran.route) {
                AlQuranScreen(
                    context,
                    navigateToSurah = { idSurah, surahName, ayat ->
                        navController.navigate(Screen.Surah.createRoute(
                            idSurah,
                            surahName,
                            ayat
                        ))
                    }
                )
            }
            composable(
                route = Screen.Surah.route,
                arguments = listOf(
                    navArgument("surahId") { type = NavType.StringType },
                    navArgument("surahName") { type = NavType.StringType },
                    navArgument("ayat") { type = NavType.IntType }
                )
            ) {
                val surahId = it.arguments?.getString("surahId")
                val surahName = it.arguments?.getString("surahName")
                val ayat = it.arguments?.getInt("ayat")
                SurahScreen(
                    context = context,
                    surahId = surahId!!,
                    surahName = surahName!!,
                    ayat = ayat!!,
                    onClickBack = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.AllMateri.route) {
                AllMaterialScreen(context)
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            BottomBarItem(
                title = "Beranda",
                icon = Icons.Outlined.Home,
                screen = Screen.Home
            ),
            BottomBarItem(
                title = "Materi",
                icon = Icons.Outlined.Book,
                screen = Screen.AllMateri
            ),
            BottomBarItem(
                title = "Al-Qur'an",
                icon = Icons.Outlined.Book,
                screen = Screen.AlQuran
            ),
            BottomBarItem(
                title = "Profil",
                icon = Icons.Outlined.Person,
                screen = Screen.Profile
            ),
        )
        navigationItems.map {
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Color.White,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.White,
                    unselectedIconColor = Color.White
                ),
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(
                        text = it.title,
                        color = Color.White
                    )
                },
                selected = currentRoute == it.screen.route,
                onClick = {
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
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