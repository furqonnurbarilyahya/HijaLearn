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
    Materi(1,1,"Alif",R.drawable.alif,R.raw.alif_t1),
    Materi(2,1,"Ba",R.drawable.ba,R.raw.ba_t1),
    Materi(3,1,"Ta",R.drawable.ta,R.raw.ta_t1)
)
