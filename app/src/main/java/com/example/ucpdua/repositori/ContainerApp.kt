package com.example.ucpdua.repositori

import android.content.Context
import com.example.ucpdua.room.PerpustakaanDatabase

interface AppContainer {
    val repositoriMatakuliah: RepositoriMatakuliah
    val repositoriProgramstudi: RepositoriProgramstudi
}

class ContainerDataApp(private val context: Context) : AppContainer {

    private val database: PerpustakaanDatabase by lazy {
        PerpustakaanDatabase.getDatabase(context)
    }

    override val repositoriMatakuliah: RepositoriMatakuliah by lazy {
        OfflineRepositoriMatakuliah(database.matakuliahDao())
    }

    override val repositoriProgramstudi: RepositoriProgramstudi by lazy {
        OfflineRepositoriProgramstudi(database.programStudiDao())
    }
}