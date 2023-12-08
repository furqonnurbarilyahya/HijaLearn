package com.bangkit.hijalearn.data

import com.bangkit.hijalearn.data.local.database.Materi
import com.bangkit.hijalearn.data.local.database.Modul
import com.bangkit.hijalearn.data.local.database.Pendahuluan

object TempInitialDataSource {
    fun getModul(): List<Modul> {
        return listOf(
            Modul(1,"Huruf Hijaiyah","Siswa akan belajar tentang pengenalan dan pengucapan huruf hijaiyah","https://thumb.viva.co.id/media/frontend/thumbs3/2022/04/07/624e6885c28a4-huruf-hijaiyah_665_374.jpg"),
            Modul(2, "Tanda Baca Fathah","Siswa akan belajar tentang pengenalan dan pengucapan tanda baca fathah","https://thumb.viva.co.id/media/frontend/thumbs3/2022/04/07/624e6885c28a4-huruf-hijaiyah_665_374.jpg"),
            Modul(3, "Tanda Baca Kasrah","Siswa akan belajar tentang pengenalan dan pengucapan tanda baca kasrah","https://thumb.viva.co.id/media/frontend/thumbs3/2022/04/07/624e6885c28a4-huruf-hijaiyah_665_374.jpg")
        )
    }

    fun getPendahuluan(): List<Pendahuluan> {

        return listOf(
            Pendahuluan(
                1,
                1,
                "Huruf Hijaiyah sebagai Dasar Ejaan Alquran",
                "Bagi umat Muslim, mengenal huruf hijaiyah adalah hal penting sebagai dasar untuk mempelajari huruf-huruf ejaan Alquran.\n" +
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
    }

    fun getMateri(): List<Materi> {
        return listOf(
            Materi(1, 1, 1, "Huruf Alif","Alif","https://i.pinimg.com/564x/62/24/b7/6224b7d7f636e1f613a011f486f3c4fc.jpg","https://commondatastorage.googleapis.com/codeskulptor-assets/week7-bounce.m4a"),
            Materi(2, 1, 2, "Huruf Ba","Ba","https://i.pinimg.com/564x/3f/58/67/3f5867cbcb354c2832e8a75092650259.jpg","https://commondatastorage.googleapis.com/codeskulptor-assets/week7-bounce.m4a"),
            Materi(3, 1, 3, "Huruf Ta","Ta","https://i.pinimg.com/564x/9e/80/cf/9e80cf8eda29d9f0b6c739566a498420.jpg","https://commondatastorage.googleapis.com/codeskulptor-assets/week7-bounce.m4a")
        )
    }
}