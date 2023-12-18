package com.bangkit.hijalearn.navigation

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome")
    object Login: Screen("login")
    object Register: Screen("register")
    object Home: Screen("home")
    object Introduction: Screen("introduction/{id}/{namaModul}/{desc}") {
        fun createRoute(id: Int,namaModul: String, desc: String) = "introduction/$id/$namaModul/$desc"
    }
    object ToListMateri: Screen("toListMateri/{modulId}/{namaModul}/{desc}") {
        fun createRoute(id: Int,namaModul: String, desc: String) = "toListMateri/$id/$namaModul/$desc"
    }
    object Materi: Screen("materi/{nomor}/{modulId}/{namaModul}") {
        fun createRoute(nomor: Int,modulId: Int,namaModul: String) = "materi/$nomor/$modulId/$namaModul"
    }
    object AlQuran: Screen("AlQuran")
    object Profile: Screen("Profile")
    object AllMateri: Screen("AllMateri")
    object InfoProfile: Screen("InfoProfile")
    object Surah: Screen ("surah/{surahId}/{surahName}/{ayat}") {
        fun createRoute(surahId: String, surahName: String, ayat: Int) = "surah/$surahId/$surahName/$ayat"
    }
    object DetailProfile : Screen("DetailProfile")
}