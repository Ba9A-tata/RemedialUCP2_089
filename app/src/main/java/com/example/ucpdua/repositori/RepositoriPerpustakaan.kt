package com.example.ucpdua.repositori

import com.example.ucpdua.room.perpustakaan
import com.example.ucpdua.room.PerpustakaanDao
import kotlinx.coroutines.flow.Flow

interface RepositoriPerpustakaan {
    fun getAllProgramstudiStream(): Flow<List<perpustakaan>>
    fun getProgramstudiStream(id: String): Flow<perpustakaan?>
    suspend fun insertProgramstudi(perpustakaan: perpustakaan)
    suspend fun deleteProgramstudi(perpustakaan: perpustakaan)
    suspend fun updateProgramstudi(perpustakaan: perpustakaan)
}

class OfflineRepositoriPerpustakaan(
    private val perpustakaanDao: PerpustakaanDao
): RepositoriPerpustakaan {

    override fun getAllProgramstudiStream(): Flow<List<perpustakaan>> =
        perpustakaanDao.getAllProgramstudi()

    override fun getProgramstudiStream(id: String): Flow<perpustakaan?> =
        perpustakaanDao.getProgramstudi(id)

    override suspend fun insertProgramstudi(perpustakaan: perpustakaan) =
        perpustakaanDao.insertProgramstudi(perpustakaan)

    override suspend fun deleteProgramstudi(perpustakaan: perpustakaan) =
        perpustakaanDao.deleteProgramstudi(perpustakaan)

    override suspend fun updateProgramstudi(perpustakaan: perpustakaan) =
        perpustakaanDao.updateProgramstudi(perpustakaan)
}