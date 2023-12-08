package com.bangkit.hijalearn.data.local.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "modul")
data class Modul(
    @PrimaryKey
    @ColumnInfo(name = "modulId")
    val modulId: Int,
    @ColumnInfo(name = "namaModul")
    val namaModul: String,
    @ColumnInfo(name = "desc")
    val deskripsi: String,
    @ColumnInfo(name = "gambarModul")
    val gambarModul: String
)


@Entity(tableName = "pendahuluan")
data class Pendahuluan(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "modulId")
    val modulId: Int,
    @ColumnInfo(name = "title1")
    val title1: String,
    @ColumnInfo(name = "desc1")
    val desc1: String,
    @ColumnInfo(name = "title2")
    val title2: String,
    @ColumnInfo(name = "desc2")
    val desc2: String
)

@Entity(tableName = "materi")
data class Materi(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "modulId")
    val modulId: Int,
    @ColumnInfo(name = "nomor")
    val nomor: Int,
    @ColumnInfo(name = "namaMateri")
    val namaMateri: String,
    @ColumnInfo(name ="caraEja")
    val caraEja: String,
    @ColumnInfo(name = "namaGambar")
    val namaGambar: String,
    @ColumnInfo(name = "namaAudio")
    val namaAudio: String,
)

data class PendahuluanAndModul(
    @Embedded
    val pendahuluan: Pendahuluan,

    @Relation(
        parentColumn = "modulId",
        entityColumn = "modulId"
    )
    val modul: Modul? = null
)