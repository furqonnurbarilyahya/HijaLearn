package com.bangkit.hijalearn.model

data class Introduction (
    val id: Int,
    val judul: String,
    val judul1: String,
    val desc1: String,
    val judul2: String,
    val desc2: String
)

val dummyIntroduction = listOf(
    Introduction(
        1,
        "Pendahuluan tentang huruf hijaiyah",
        "Huruf Hijaiyah sebagai Dasar Ejaan Alquran",
        "Bagi umTeat Muslim, mengenal huruf hijaiyah adalah hal penting sebagai dasar untuk mempelajari huruf-huruf ejaan Alquran.\n" +
                "\n" +
                "Umumnya, tata cara membaca huruf hijaiyah mulai diperkenalkan pada anak-anak usia sekolah, seperti jenjang pendidikan anak usia dini (PAUD) serta sekolah dasar (SD).\n" +
                "\n" +
                "Selain dihafal, anak-anak tersebut harus mampu menuliskan huruf hijaiyah dan membacanya dalam latihan mengaji.",
        "Apa Saja Huruf Hijaiyah?",
        "Dalam buku Metode Pembelajaran Baca Tulis Al-Quran: Memaksimalkan Pendidikan Islam melalui Al-Quran karya Mursal Aziz dan Zulkipli Nasution, huruf hijaiyah jumlahnya banyak.\n" +
                "\n" +
                "Huruf hijaiyah terdiri atas 28 huruf tunggal atau 30 jika memasukkan huruf rangkap lam-alif (لا) dan hamzah (ء) sebagai huruf yang berdiri sendiri.\n" +
                "\n" +
                "Huruf hijaiyah yang berjumlah 30 ini pertama kali disusun secara berurutan oleh Nashr bin 'Ashim al-Laitsi."
    )
)