package com.example.ucpdua.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tblProgramstudi")
data class perpustakaan(
    @PrimaryKey
    val namaProdi: String
)