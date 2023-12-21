package com.bangkit.hijalearn.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Modul::class,Pendahuluan::class,Materi::class],
    version = 1,
    exportSchema = false
)

abstract class ModuleDatabase : RoomDatabase() {
    abstract fun moduleDao(): ModuleDao
    companion object {
        @Volatile
        private var INSTANCE: ModuleDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context, applicationScope: CoroutineScope): ModuleDatabase {
            if (INSTANCE == null) {
                synchronized(ModuleDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ModuleDatabase::class.java, "module_database.db"
                    )
                        .fallbackToDestructiveMigration()
                        .createFromAsset("module_database.db")
//                        .addCallback(object : Callback() {
//                            override fun onCreate(db: SupportSQLiteDatabase) {
//                                super.onCreate(db)
//                                Log.d("Database", "Database created. Inserting initial data...")
//                                INSTANCE?.let { database ->
//                                    applicationScope.launch {
//                                        val moduleDao = database.moduleDao()
//                                        moduleDao.insertModul(TempInitialDataSource.getModul())
//                                        moduleDao.insertPendahuluan(TempInitialDataSource.getPendahuluan())
//                                        moduleDao.insertMateri(TempInitialDataSource.getMateri())
//                                        Log.d("Database", "Finish")
//                                    }
//                                }
//                            }
//                        })
                        .build()
                }
            }
            return INSTANCE as ModuleDatabase
        }
    }
}