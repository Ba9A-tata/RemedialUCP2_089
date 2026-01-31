package com.example.ucpdua.repositori

import com.example.ucpdua.room.Buku
import com.example.ucpdua.room.BukuDao
import kotlinx.coroutines.flow.Flow

interface RepositoriMatakuliah {
    fun getAllMatakuliahStream(): Flow<List<Buku>>

    fun getMatakuliahByProdiStream(namaProdi: String): Flow<List<Buku>>

    // Detail mata kuliah berdasarkan NIM
    fun getMatakuliahStream(nim: String): Flow<Buku?>

    suspend fun insertMatakuliah(buku: Buku)

    suspend fun deleteMatakuliah(buku: Buku)

    suspend fun updateMatakuliah(buku: Buku)
}

class OfflineRepositoriMatakuliah(
    private val bukuDao: BukuDao
) : RepositoriMatakuliah {

    override fun getAllMatakuliahStream(): Flow<List<Buku>> =
        bukuDao.getAllMatakuliah()

    override fun getMatakuliahByProdiStream(namaProdi: String): Flow<List<Buku>> =
        bukuDao.getMatakuliahByProdi(namaProdi)

    override fun getMatakuliahStream(nim: String): Flow<Buku?> =
        bukuDao.getMatakuliah(nim)

    override suspend fun insertMatakuliah(buku: Buku) =
        bukuDao.insertMatakuliah(buku)

    override suspend fun deleteMatakuliah(buku: Buku) =
        bukuDao.deleteMatakuliah(buku)

    override suspend fun updateMatakuliah(buku: Buku) =
        bukuDao.updateMatakuliah(buku)
}