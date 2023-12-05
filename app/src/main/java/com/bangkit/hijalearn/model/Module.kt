package com.bangkit.hijalearn.model

import com.bangkit.hijalearn.R

data class Module (
    val id: Int,
    val image: Int,
    val title: String,
    val description: String
)

val dummyModule = listOf(
    Module(1,R.drawable.banner, "Modul 1 | Huruf Hijaiyah", "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah."),
    Module(1,R.drawable.banner, "Modul 2 | Huruf Hijaiyah", "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah."),
    Module(1,R.drawable.banner, "Modul 3 | Huruf Hijaiyah", "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah.")
)