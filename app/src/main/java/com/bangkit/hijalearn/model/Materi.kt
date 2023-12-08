package com.bangkit.hijalearn.model
import com.bangkit.hijalearn.R

data class Materi (
    val id: Int,
    val modul: Int,
    val namaHuruf: String,
    val gambar: Int,
    val audio: Int,
    val selesai: Boolean = false
)


val dummyMateri = listOf(
    Materi(1,1,"Alif",R.drawable.modul_1_alif,R.raw.modul_1_alif),
    Materi(2,1,"Ba",R.drawable.modul_1_ba,R.raw.module_1_ba),
    Materi(3,1,"Ta",R.drawable.modul_1_ta,R.raw.module_1_ta)
)
