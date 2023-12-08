package com.bangkit.hijalearn.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ModuleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertModul(modul: List<Modul>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPendahuluan(pendahuluan: List<Pendahuluan>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMateri(materi: List<Materi>)

    @Query("SELECT * FROM modul")
    suspend fun getAllModul() : List<Modul>

//    @Query(
//        "SELECT * FROM pendahuluan "+
//        "INNER JOIN modul ON pendahuluan.modulId = modul.modulId " +
//        "WHERE pendahuluan.modulId = :modulId"
//    )
    @Query(
        "SELECT * FROM pendahuluan WHERE modulId = :modulId"
    )
    suspend fun getPendahuluanWithModulById(modulId: Int): Pendahuluan

//    @Query(
//        "SELECT * " +
//        "FROM Materi " +
//        "INNER JOIN Modul ON Materi.modulId = Modul.modulId " +
//        "WHERE Materi.modulId = :modulId"
//    )
//    suspend fun getAllMateriWithModuleById(modulId: Int): List<MateriWithModul>

    @Query("SELECT * FROM materi WHERE modulId = :modulId")
    suspend fun getListMateriByModulId(modulId: Int): List<Materi>
    @Query(
        "SELECT * FROM materi "+
        "WHERE nomor = :nomor AND modulId = :modulId"
    )
    suspend fun getMateriByNomorAndModulId(nomor:Int,modulId: Int) : Materi
}