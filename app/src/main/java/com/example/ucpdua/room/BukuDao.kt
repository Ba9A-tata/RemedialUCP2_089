package com.example.ucpdua.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BukuDao {

    @Query("SELECT * FROM tblMatakuliah ORDER BY nama ASC")
    fun getAllMatakuliah(): Flow<List<Buku>>

    @Query("SELECT * FROM tblMatakuliah WHERE namaProdi = :namaProdi ORDER BY nama ASC")
    fun getMatakuliahByProdi(namaProdi: String): Flow<List<Buku>>

    @Query("SELECT * FROM tblMatakuliah WHERE nama LIKE :search OR namaProdi LIKE :search OR dosen LIKE :search")
    fun searchMatakuliah(search: String): Flow<List<Buku>>

    @Query("SELECT * FROM tblMatakuliah WHERE nim = :nim")
    fun getMatakuliah(nim: String): Flow<Buku?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMatakuliah(buku: Buku)

    @Delete
    suspend fun deleteMatakuliah(buku: Buku)

    @Update
    suspend fun updateMatakuliah(buku: Buku)
}