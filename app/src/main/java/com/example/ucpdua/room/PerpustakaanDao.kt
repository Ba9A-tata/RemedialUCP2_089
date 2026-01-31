package com.example.ucpdua.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PerpustakaanDao {

    @Query("SELECT * FROM tblProgramstudi ORDER BY namaProdi ASC")
    fun getAllProgramstudi(): Flow<List<perpustakaan>>

    @Query("SELECT * FROM tblProgramstudi WHERE namaProdi = :namaProdi")
    fun getProgramstudi(namaProdi: String): Flow<perpustakaan?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProgramstudi(perpustakaan: perpustakaan)

    @Delete
    suspend fun deleteProgramstudi(perpustakaan: perpustakaan)

    @Update
    suspend fun updateProgramstudi(perpustakaan: perpustakaan)
}