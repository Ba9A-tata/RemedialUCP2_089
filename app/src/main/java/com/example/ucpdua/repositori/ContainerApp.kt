package com.example.ucpdua.repositori

import android.content.Context
import com.example.ucpdua.room.PerpustakaanDatabase

interface AppContainer {
    val repositoriBuku: RepositoriBuku
    val repositoriPerpustakaan: RepositoriPerpustakaan
}

class ContainerDataApp(private val context: Context) : AppContainer {

    private val database: PerpustakaanDatabase by lazy {
        PerpustakaanDatabase.getDatabase(context)
    }

    override val repositoriBuku: RepositoriBuku by lazy {
        OfflineRepositoriBuku(database.matakuliahDao())
    }

    override val repositoriPerpustakaan: RepositoriPerpustakaan by lazy {
        OfflineRepositoriPerpustakaan(database.programStudiDao())
    }
}