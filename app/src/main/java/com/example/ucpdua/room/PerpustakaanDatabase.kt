package com.example.ucpdua.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Buku::class, perpustakaan::class], version = 1, exportSchema = false)
abstract class PerpustakaanDatabase : RoomDatabase() {

    abstract fun matakuliahDao(): BukuDao
    abstract fun programStudiDao(): PerpustakaanDao

    companion object {
        @Volatile
        private var Instance: PerpustakaanDatabase? = null

        fun getDatabase(context: Context): PerpustakaanDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    PerpustakaanDatabase::class.java,
                    "AkademikDatabase"
                ).build().also { Instance = it }
            }
        }
    }
}