package com.bangkit.hijalearn.model

import com.bangkit.hijalearn.R

data class Module (
    val image: Int,
    val title: String,
    val description: String
)

val dummyModule = listOf(
    Module(R.drawable.banner, "Modul 1 | Huruf Hijaiyah", "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah."),
    Module(R.drawable.banner, "Modul 2 | Huruf Hijaiyah", "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah."),
    Module(R.drawable.banner, "Modul 3 | Huruf Hijaiyah", "Kamu akan belajar tentang pengenalan dan pengucapan huruf hijaiyah.")
)