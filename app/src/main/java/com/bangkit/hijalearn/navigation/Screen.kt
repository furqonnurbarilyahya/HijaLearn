package com.bangkit.hijalearn.navigation

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome")
    object Login: Screen("login")
    object Register: Screen("register")
    object Home: Screen("home")
    object Introduction: Screen("introduction/{id}") {
        fun createRoute(id: Int) = "introduction/$id"
    }
    object ToListMateri: Screen("toListMateri/{modulId}") {
        fun createRoute(modulId: Int) = "toListMateri/$modulId"
    }
    object Materi: Screen("materi/{materiId}") {
        fun createRoute(materiId: Int) = "materi/$materiId"
    }
}